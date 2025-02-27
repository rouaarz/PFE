package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cni.plateformetesttechnique.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeveloppeurResponseService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private DeveloppeurResponseRepository developpeurResponseRepository;

    @Autowired
    private DeveloppeurRepository developpeurRepository;  // Assurez-vous que c'est bien cette variable qui est utilisée
    @Autowired
    private TestQuestionRepository testQuestionRepository;
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;
    public void enregistrerReponse(Long testId, Long questionId, List<Long> selectedOptionIds, Long developpeurId) {
        // Récupérer le développeur
        Developpeur developpeur = developpeurRepository.findById(developpeurId)
                .orElseThrow(() -> new RuntimeException("Développeur non trouvé avec ID: " + developpeurId));

        // Récupérer le test et la question
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé avec ID: " + testId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question non trouvée avec ID: " + questionId));

        // Vérifier que la question appartient bien au test
        // Vérifier que la question appartient bien au test
        boolean questionInTest = test.getTestQuestions().stream()
                .anyMatch(tq -> tq.getQuestion().getId().equals(questionId));

        if (!questionInTest) {
            throw new RuntimeException("Cette question n'appartient pas au test spécifié.");
        }


        // Récupérer les options sélectionnées
        List<AnswerOption> selectedOptions = answerOptionRepository.findAllByIdIn(selectedOptionIds);

        // Vérifier que toutes les options sélectionnées appartiennent bien à la question
        boolean allOptionsBelongToQuestion = selectedOptions.stream()
                .allMatch(option -> option.getQuestion().getId().equals(questionId));

        if (!allOptionsBelongToQuestion) {
            throw new RuntimeException("Certaines options sélectionnées n'appartiennent pas à cette question.");
        }

        // Vérifier si toutes les options sélectionnées sont correctes
        boolean isCorrect = selectedOptions.stream().allMatch(AnswerOption::getIsCorrect);

        // Enregistrer la réponse
        DeveloppeurResponse developpeurResponse = new DeveloppeurResponse(test, question, selectedOptions, isCorrect, developpeur);
        developpeurResponseRepository.save(developpeurResponse);

        // Optionnel : Afficher un message de succès
        System.out.println("Réponse enregistrée avec succès pour le développeur : " + developpeur.getEmail());
    }
    @Transactional
    public void supprimerReponses(Long testId, Long developpeurId) {
        // Récupérer le développeur
        Developpeur developpeur = developpeurRepository.findById(developpeurId)
                .orElseThrow(() -> new RuntimeException("Développeur non trouvé avec ID: " + developpeurId));

        // Vérifier si le développeur a des réponses enregistrées pour ce test
        List<DeveloppeurResponse> responses = developpeurResponseRepository.findByTest_IdAndDeveloppeur_Id(testId, developpeurId);

        if (responses.isEmpty()) {
            System.out.println("Aucune réponse trouvée pour ce développeur dans ce test.");
            return;
        }

        // Affichage du nombre de réponses à supprimer
        System.out.println("Réponses trouvées à supprimer pour le développeur : " + developpeur.getEmail());

        // Supprimer les réponses associées
        for (DeveloppeurResponse response : responses) {
            developpeurResponseRepository.delete(response);
            System.out.println("Réponse supprimée pour la question ID : " + response.getQuestion().getId());
        }

        // Optionnel : supprimer aussi les options associées si nécessaire
        for (DeveloppeurResponse response : responses) {
            List<AnswerOption> selectedOptions = response.getSelectedAnswerOptions();
            for (AnswerOption option : selectedOptions) {
                answerOptionRepository.delete(option); // Si tu souhaites aussi supprimer les options, mais cela dépend du cas d'utilisation.
                System.out.println("Option de réponse supprimée : " + option.getId());
            }
        }

        // Optionnel : Message de confirmation
        System.out.println("Toutes les réponses pour le test ID " + testId + " ont été supprimées pour le développeur " + developpeur.getEmail());
    }


}
