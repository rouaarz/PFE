package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.Question;
import com.cni.plateformetesttechnique.model.Test;
import com.cni.plateformetesttechnique.model.TestQuestion;
import com.cni.plateformetesttechnique.repository.QuestionRepository;
import com.cni.plateformetesttechnique.repository.TestQuestionRepository;
import com.cni.plateformetesttechnique.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestQuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestQuestionRepository testQuestionRepository;
    @Autowired
    private TestRepository testRepository;

    public List<Question> getQuestionsForTest(Long testId) {
        List<TestQuestion> testQuestions = testQuestionRepository.findByTestId(testId);
        List<Question> questions = new ArrayList<>();
        for (TestQuestion testQuestion : testQuestions) {
            questions.add(testQuestion.getQuestion());
        }
        return questions;
    }

    public List<TestQuestion> addQuestionsToTest(Long testId, List<TestQuestion> testQuestions) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé"));

        if (!"BROUILLON".equals(test.getStatut())) {
            throw new RuntimeException("Impossible d'ajouter des questions à un test déjà publié");
        }

        List<TestQuestion> savedTestQuestions = new ArrayList<>();

        for (TestQuestion tq : testQuestions) {
            Question question = questionRepository.findById(tq.getQuestion().getId())
                    .orElseThrow(() -> new RuntimeException("Question non trouvée : ID " + tq.getQuestion().getId()));

            boolean alreadyExists = testQuestionRepository.findByTestId(testId).stream()
                    .anyMatch(q -> q.getQuestion().getId().equals(question.getId()));

            if (alreadyExists) {
                throw new RuntimeException("La question ID " + question.getId() + " est déjà associée à ce test");
            }

            TestQuestion newTestQuestion = new TestQuestion(test, question, tq.getPoints(), tq.getOrdre());
            savedTestQuestions.add(testQuestionRepository.save(newTestQuestion));
        }

        return savedTestQuestions;
    }
    public void removeQuestionFromTest(Long testId, Long questionId) {
        // Vérifier l'existence de l'association Test-Question
        TestQuestion testQuestion = testQuestionRepository.findByTestIdAndQuestionId(testId, questionId)
                .orElseThrow(() -> new RuntimeException("Association Test-Question non trouvée"));

        // Supprimer l'association sans supprimer la question elle-même
        testQuestionRepository.delete(testQuestion);
    }

    public TestQuestionService(TestQuestionRepository testQuestionRepository) {
        this.testQuestionRepository = testQuestionRepository;
    }

    // Méthode pour ajouter une TestQuestion
    public TestQuestion addTestQuestion(TestQuestion testQuestion) {
        return testQuestionRepository.save(testQuestion);
    }
}
