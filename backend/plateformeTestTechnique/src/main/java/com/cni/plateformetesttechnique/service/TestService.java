package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.Test;
import com.cni.plateformetesttechnique.model.Administrateur;
import com.cni.plateformetesttechnique.model.Question;
import com.cni.plateformetesttechnique.model.TestQuestion;
import com.cni.plateformetesttechnique.repository.TestRepository;
import com.cni.plateformetesttechnique.repository.QuestionRepository;
import com.cni.plateformetesttechnique.repository.TestQuestionRepository;

import com.cni.plateformetesttechnique.repository.AdministrateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TestQuestionRepository testQuestionRepository; // Correction ici

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


}
