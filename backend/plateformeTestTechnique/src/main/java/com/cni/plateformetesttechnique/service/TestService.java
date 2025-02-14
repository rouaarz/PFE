package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.*;
import com.cni.plateformetesttechnique.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private AdministrateurRepository administrateurRepository;
    @Autowired
    private QuestionRepository questionRepository; // Correction ici
    @Autowired
    private DeveloppeurRepository developpeurRepository; // ✅ Ajout de l’injection du repository

    @Autowired
    private TestQuestionRepository testQuestionRepository; // Correction ici
    @Autowired
    private InvitationTestRepository invitationTestRepository;
    @Autowired
    private EmailService emailService;

    public Test createTest(Test test, Long adminId) {
        // Vérifier si l'administrateur existe
        Administrateur admin = administrateurRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));

        // Définir les valeurs par défaut
        test.setAdministrateur(admin);
        test.setStatut("BROUILLON");
        test.setDateCreation(LocalDateTime.now());

        // Sauvegarde du test
        return testRepository.save(test);
    }
    public Test updateTest(Long testId, Test updatedTest) {
        // Vérifier si le test existe
        Test existingTest = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        // Vérifier si le test est encore en brouillon
        if (!"BROUILLON".equals(existingTest.getStatut())) {
            throw new RuntimeException("Impossible de modifier un test qui n'est pas en brouillon");
        }

        // Mettre à jour les champs modifiables
        existingTest.setTitre(updatedTest.getTitre());
        existingTest.setDescription(updatedTest.getDescription());
        existingTest.setDuree(updatedTest.getDuree());
        existingTest.setType(updatedTest.getType());
        existingTest.setAccesPublic(updatedTest.getAccesPublic());
        existingTest.setLimiteTentatives(updatedTest.getLimiteTentatives());
        existingTest.setDateExpiration(updatedTest.getDateExpiration());

        // Sauvegarder les modifications
        return testRepository.save(existingTest);
    }
    public List<Question> getQuestionsForTest(Long testId) {
        // Récupérer les questions associées au test
        List<TestQuestion> testQuestions = testQuestionRepository.findByTestId(testId);

        // Extraire les questions de l'association TestQuestion
        List<Question> questions = new ArrayList<>();
        for (TestQuestion testQuestion : testQuestions) {
            questions.add(testQuestion.getQuestion());
        }

        return questions; // Retourner la liste des questions
    }

    public List<TestQuestion> addQuestionsToTest(Long testId, List<TestQuestion> testQuestions) {
        // Vérifier si le test existe
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        // Vérifier que le test est en brouillon
        if (!"BROUILLON".equals(test.getStatut())) {
            throw new RuntimeException("Impossible d'ajouter des questions à un test déjà publié");
        }

        List<TestQuestion> savedTestQuestions = new ArrayList<>();

        for (TestQuestion tq : testQuestions) {
            // Vérifier que la question existe
            Question question = questionRepository.findById(tq.getQuestion().getId()) // Correction ici
                    .orElseThrow(() -> new RuntimeException("Question non trouvée : ID " + tq.getQuestion().getId()));

            // Vérifier que la question n'est pas déjà associée au test
            boolean alreadyExists = testQuestionRepository.findByTestId(testId).stream() // Correction ici
                    .anyMatch(q -> Long.valueOf(q.getQuestion().getId()).equals(Long.valueOf(question.getId()))); // Correction ici

            if (alreadyExists) {
                throw new RuntimeException("La question ID " + question.getId() + " est déjà associée à ce test");
            }

            // Créer et enregistrer l'association Test - Question
            TestQuestion newTestQuestion = new TestQuestion(test, question, tq.getPoints(), tq.getOrdre());

            savedTestQuestions.add(testQuestionRepository.save(newTestQuestion)); // Correction ici
        }

        return savedTestQuestions;
    }
    public Test getTestDetails(Long testId) {
        // Vérifier si le test existe
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));

        // Charger les questions associées avec ordre et points
        List<TestQuestion> testQuestions = testQuestionRepository.findByTestId(testId);

        // Ajouter les questions récupérées dans l'objet test
        test.setTestQuestions(testQuestions);

        return test;
    }
    public void sendInvitationEmails(Test test) {
        List<InvitationTest> invitations = invitationTestRepository.findByTest(test);

        for (InvitationTest invitation : invitations) {
            Developpeur developer = invitation.getDeveloppeur();
            emailService.sendTestPublishedNotification(test, developer);
        }
    }
    public Test publishTest(Long testId, Boolean accesRestreint) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));

        // Vérifier si le test est en brouillon avant de le publier
        if (!"BROUILLON".equals(test.getStatut())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seuls les tests en brouillon peuvent être publiés");
        }

        // Vérifier que le test contient au moins une question avant publication
        if (testQuestionRepository.findByTestId(testId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un test doit contenir au moins une question avant d'être publié");
        }

        // Mise à jour du statut et accès restreint
        test.setStatut("PUBLIE");
        test.setAccesPublic(accesRestreint);
        test.setDateExpiration(LocalDateTime.now().plusDays(30)); // Archivage après 30 jours

        Test publishedTest = testRepository.save(test);

        // Si le test est sur invitation, envoyer un email aux développeurs invités
        if (accesRestreint) {
            sendInvitationEmails(test);
        }

        return publishedTest;
    }
    public void inviteDevelopers(Long testId, List<Long> developerIds) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));

        if (!"PUBLIE".equals(test.getStatut())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vous devez publier le test avant d'inviter des développeurs");
        }

        for (Long devId : developerIds) {
            Developpeur developer = developpeurRepository.findById(devId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Développeur non trouvé"));

            boolean alreadyInvited = invitationTestRepository.findByTestAndDeveloppeur(test, developer).isPresent();
            if (alreadyInvited) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le développeur ID " + devId + " est déjà invité");
            }

            InvitationTest invitation = new InvitationTest();
            invitation.setTest(test);
            invitation.setDeveloppeur(developer);
            invitation.setStatut("PENDING");
            invitation.setDateInvitation(LocalDateTime.now());

            invitationTestRepository.save(invitation);

            System.out.println(" Invitation enregistrée pour : " + developer.getEmail()); // ✅ Debug

            //  Vérifier si l'email est bien appelé ici
            emailService.sendInvitationEmail(invitation);
            System.out.println(" Email envoyé à : " + developer.getEmail()); // ✅ Debug
        }
    }




}
