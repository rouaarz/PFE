package com.cni.plateformetesttechnique.controller;

import com.cni.plateformetesttechnique.model.Question;
import com.cni.plateformetesttechnique.model.TestQuestion;
import com.cni.plateformetesttechnique.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-questions")
public class TestQuestionController {

    @Autowired
    private TestQuestionService testQuestionService;

    // Ajouter des questions à un test
    @PostMapping("/add/{testId}")
    public ResponseEntity<List<TestQuestion>> addQuestionsToTest(
            @PathVariable Long testId,
            @RequestBody List<TestQuestion> testQuestions) {
        List<TestQuestion> savedTestQuestions = testQuestionService.addQuestionsToTest(testId, testQuestions);
        return ResponseEntity.ok(savedTestQuestions);
    }

    // Récupérer les questions d'un test
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<Question>> getQuestionsForTest(@PathVariable Long testId) {
        List<Question> questions = testQuestionService.getQuestionsForTest(testId);
        return ResponseEntity.ok(questions);
    }

    // Supprimer une question d'un test
    @DeleteMapping("/remove/{testId}/{questionId}")
    public ResponseEntity<Object> removeQuestionFromTest(@PathVariable Long testId, @PathVariable Long questionId) {
        try {
            testQuestionService.removeQuestionFromTest(testId, questionId);
            // Réponse JSON avec statut et message
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    "{ \"status\": \"Succès\", \"message\": \"La question a été supprimée du test avec succès.\" }"
            );
        } catch (Exception e) {
            // En cas d'erreur, retour d'un message JSON d'erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{ \"status\": \"Erreur\", \"message\": \"Impossible de supprimer la question : " + e.getMessage() + "\" }"
            );
        }
    }
}
