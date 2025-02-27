package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.*;
import com.cni.plateformetesttechnique.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ScoreService {
    @Autowired
    private DeveloppeurResponseRepository developpeurResponseRepository;
    @Autowired
    private TestQuestionRepository testQuestionRepository;
    @Autowired
    private DeveloppeurRepository developpeurRepository;
    @Autowired
    private DeveloppeurTestScoreRepository developpeurTestScoreRepository;
    @Autowired
    private TestRepository testRepository;
    public Double calculerScore(Long testId, Long developpeurId) {
        // Récupérer toutes les réponses du développeur pour ce test
        List<DeveloppeurResponse> responses = developpeurResponseRepository.findByTest_IdAndDeveloppeur_Id(testId, developpeurId);

        // Récupérer toutes les questions du test
        List<TestQuestion> testQuestions = testQuestionRepository.findByTestId(testId);
        if (responses.size() < testQuestions.size()) {
            throw new RuntimeException("Le développeur n'a pas encore complété toutes les questions du test !");
        }
        // Calculer le total des points du test
        double totalPoints = testQuestions.stream().mapToDouble(TestQuestion::getPoints).sum();

        // Calculer le score
        double pointsObtenus = 0;
        for (DeveloppeurResponse response : responses) {
            if (response.getIsCorrect()) {
                // Trouver les points associés à cette question
                TestQuestion testQuestion = testQuestions.stream()
                        .filter(tq -> tq.getQuestion().getId().equals(response.getQuestion().getId()))
                        .findFirst()
                        .orElse(null);

                if (testQuestion != null) {
                    pointsObtenus += testQuestion.getPoints();
                }
            }
        }

        // Calculer le score en pourcentage
        double scoreFinal = (pointsObtenus / totalPoints) * 100;

        // Créer une instance de DeveloppeurTestScore et l'enregistrer
        Developpeur developpeur = developpeurRepository.findById(developpeurId)
                .orElseThrow(() -> new RuntimeException("Développeur non trouvé"));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        DeveloppeurTestScore developpeurTestScore = new DeveloppeurTestScore(developpeur, test, scoreFinal);

        // Sauvegarder le score
        developpeurTestScoreRepository.save(developpeurTestScore);
// Mettre à jour le score global du développeur
        updateGlobalScore(developpeur);
        // Retourner le score final en pourcentage
        return scoreFinal;
    }
    private void updateGlobalScore(Developpeur developpeur) {
        // Récupérer tous les scores du développeur dans tous les tests qu'il a passés
        List<DeveloppeurTestScore> scores = developpeurTestScoreRepository.findByDeveloppeur(developpeur);

        // Calculer la moyenne des scores
        double globalScore = scores.stream().mapToDouble(DeveloppeurTestScore::getScore).average().orElse(0.0);

        // Mettre à jour le score du développeur
        developpeur.setScore(globalScore);

        // Sauvegarder le développeur avec son score mis à jour
        developpeurRepository.save(developpeur);

        System.out.println("Score global mis à jour pour le développeur : " + globalScore);
    }
//    public Double calculerScore(Long testId, Long developpeurId) {
//        // Récupérer toutes les réponses du développeur pour ce test
//        List<DeveloppeurResponse> responses = developpeurResponseRepository.findByTest_IdAndDeveloppeur_Id(testId, developpeurId);
//
//        System.out.println("Réponses du développeur récupérées : " + responses.size() + " réponses");
//
//        // Récupérer toutes les questions du test
//        List<TestQuestion> testQuestions = testQuestionRepository.findByTestId(testId);
//
//        System.out.println("Questions du test récupérées : " + testQuestions.size() + " questions");
//
//        // Nombre total de questions dans ce test
//        int totalQuestions = testQuestions.size();
//        System.out.println("Nombre total de questions dans le test : " + totalQuestions);
//
//        // Calculer le score
//        double score = 0;
//        for (DeveloppeurResponse response : responses) {
//            System.out.println("Traitement de la réponse pour la question ID : " + response.getQuestion().getId());
//
//            if (response.getIsCorrect()) {
//                // Trouver les points associés à cette question
//                TestQuestion testQuestion = testQuestions.stream()
//                        .filter(tq -> tq.getQuestion().getId().equals(response.getQuestion().getId()))
//                        .findFirst()
//                        .orElse(null);
//
//                if (testQuestion != null) {
//                    System.out.println("Question correcte. Points associés : " + testQuestion.getPoints());
//                    score += testQuestion.getPoints();
//                } else {
//                    System.out.println("Question non trouvée dans la liste des questions du test.");
//                }
//            } else {
//                System.out.println("Réponse incorrecte pour la question ID : " + response.getQuestion().getId());
//            }
//        }
//
//        // Calculer le score en pourcentage
//        double scoreFinal = (score / totalQuestions);
//        System.out.println("Score calculé (avant pourcentage) : " + score);
//        System.out.println("Score calculé (avant pourcentage) : " + scoreFinal);
//
//        System.out.println("Score final (en pourcentage) : " + scoreFinal * 100);
//
//        return scoreFinal * 100;  // Retourne le score en pourcentage
//    }

}
