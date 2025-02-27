package com.cni.plateformetesttechnique.service;
import com.cni.plateformetesttechnique.model.Question;
import com.cni.plateformetesttechnique.model.Test;
import com.cni.plateformetesttechnique.model.TestQuestion;
import com.cni.plateformetesttechnique.model.TypeQuestion;
import com.cni.plateformetesttechnique.repository.QuestionRepository;
import com.cni.plateformetesttechnique.repository.TestQuestionRepository;
import com.cni.plateformetesttechnique.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
//public class QuestionService {
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    public Question ajouterQuestion(Question question) {
//        // Associer chaque AnswerOption à cette Question
//        question.getAnswerOptions().forEach(option -> option.setQuestion(question));
//        return questionRepository.save(question);
//    }
//
//    public List<Question> getAllQuestions() {
//        return questionRepository.findAll();
//    }
//}
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestQuestionRepository testQuestionRepository;

    // Ajouter une nouvelle question
    public Question ajouterQuestion(Question question) {
        // Associer chaque AnswerOption à cette Question
        question.getAnswerOptions().forEach(option -> option.setQuestion(question));
        return questionRepository.save(question);
    }

    // Récupérer toutes les questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Récupérer une question par son ID
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question non trouvée avec ID: " + id));
    }

    // Récupérer des questions par type (exemple: QCM, texte libre, etc.)
    public List<Question> getQuestionsByType(TypeQuestion type) {
        return questionRepository.findByType(type);
    }

    // Mettre à jour une question existante
    public Question updateQuestion(Long id, Question questionUpdated) {
        Question question = getQuestionById(id);
        question.setEnonce(questionUpdated.getEnonce());
        question.setNiveau(questionUpdated.getNiveau());
        question.setType(questionUpdated.getType());

        // Mise à jour des AnswerOptions
        question.getAnswerOptions().clear();
        question.getAnswerOptions().addAll(questionUpdated.getAnswerOptions());
        question.getAnswerOptions().forEach(option -> option.setQuestion(question));

        return questionRepository.save(question);
    }

    // Supprimer une question par son ID
    public void deleteQuestion(Long id) {
        Question question = getQuestionById(id);
        questionRepository.delete(question);
    }
    public Question ajouterQuestionAuTest(Long testId, Question question, Integer points, Integer ordre) {
        // Récupérer le test
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test non trouvé avec ID: " + testId));

        // Enregistrer d'abord la question
        question.getAnswerOptions().forEach(option -> option.setQuestion(question));
        Question savedQuestion = questionRepository.save(question);

        // Associer la question au test avec points et ordre
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setTest(test);
        testQuestion.setQuestion(savedQuestion);
        testQuestion.setPoints(points);
        testQuestion.setOrdre(ordre);

        // Sauvegarder le TestQuestion
        testQuestionRepository.save(testQuestion);

        return savedQuestion;
    }

}














//    public Question addQuestionWithTest(AddQuestionRequest addQuestionRequest, TypeQuestion type, NiveauQuestion niveau) {
//        // Créer une nouvelle question avec les données de la requête
//        Question newQuestion = new Question();
//        newQuestion.setType(type); // Type de la question
//        newQuestion.setEnonce(addQuestionRequest.getEnonce());
//        newQuestion.setReponses(addQuestionRequest.getReponses());
//        newQuestion.setNiveau(niveau); // Niveau de la question
//
//        // Assigner les indices des réponses correctes
//        List<Integer> indicesCorrectes = addQuestionRequest.getIndexReponsesCorrectes();
//        newQuestion.setIndexReponsesCorrectes(indicesCorrectes);
//
//        // Sauvegarder la question dans la base de données
//        return questionRepository.save(newQuestion);
//    }

