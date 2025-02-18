package com.cni.plateformetesttechnique.service;
import com.cni.plateformetesttechnique.model.Question;
import com.cni.plateformetesttechnique.model.TypeQuestion;
import com.cni.plateformetesttechnique.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

   
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }


    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

  
    public Question updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id)
                .map(question -> {
                    question.setType(questionDetails.getType());
                    question.setEnonce(questionDetails.getEnonce());
                    question.setReponses(questionDetails.getReponses());
                    question.setReponseCorrecte(questionDetails.getReponseCorrecte()); // Mettre à jour la réponse correcte
                    question.setNiveau(questionDetails.getNiveau());
                    return questionRepository.save(question);
                })
                .orElseThrow(() -> new RuntimeException("Question non trouvée"));
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    
    public boolean verifierReponse(Long questionId, String reponseUtilisateur) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent() && questionOpt.get().getType() == TypeQuestion.QCM) {
            return questionOpt.get().getReponseCorrecte().equalsIgnoreCase(reponseUtilisateur);
        }
        return false;
    }
    
    
    
}

