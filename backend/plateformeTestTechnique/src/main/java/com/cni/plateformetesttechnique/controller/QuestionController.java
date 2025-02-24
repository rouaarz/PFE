package com.cni.plateformetesttechnique.controller;

import com.cni.plateformetesttechnique.model.AnswerOption;
import com.cni.plateformetesttechnique.model.NiveauQuestion;
import com.cni.plateformetesttechnique.model.Question;
import com.cni.plateformetesttechnique.model.TypeQuestion;
import com.cni.plateformetesttechnique.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/questions")
//public class QuestionController {
//    @Autowired
//    private QuestionService questionService;
//
//    @PostMapping
//    public Question ajouterQuestion(@RequestBody Question question) {
//        return questionService.ajouterQuestion(question);
//    }
//
//    @GetMapping
//    public List<Question> getAllQuestions() {
//        return questionService.getAllQuestions();
//    }
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Ajouter une nouvelle question
    @PostMapping("/add")
    public Question ajouterQuestion(@RequestBody Question question) {
        return questionService.ajouterQuestion(question);
    }

    // Récupérer toutes les questions
    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Récupérer une question par son ID
    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    // Récupérer des questions par type
    @GetMapping("/type")
    public List<Question> getQuestionsByType(@RequestParam TypeQuestion type) {
        return questionService.getQuestionsByType(type);
    }

    // Mettre à jour une question existante
    @PutMapping("/update/{id}")
    public Question updateQuestion(@PathVariable Long id, @RequestBody Question questionUpdated) {
        return questionService.updateQuestion(id, questionUpdated);
    }

    // Supprimer une question par son ID
    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }
    @PostMapping("/ajouterAuTest/{testId}")
    public Question ajouterQuestionAuTest(
            @PathVariable Long testId,
            @RequestBody Map<String, Object> requestBody) {

        Question question = new Question();
        question.setEnonce((String) requestBody.get("enonce"));
        question.setNiveau(NiveauQuestion.valueOf((String) requestBody.get("niveau")));
        question.setType(TypeQuestion.valueOf((String) requestBody.get("type")));

        // Ajouter les options de réponse
        List<Map<String, Object>> options = (List<Map<String, Object>>) requestBody.get("answerOptions");
        List<AnswerOption> answerOptions = new ArrayList<>();
        for (Map<String, Object> optionData : options) {
            AnswerOption option = new AnswerOption();
            option.setText((String) optionData.get("text"));
            option.setIsCorrect((Boolean) optionData.get("isCorrect"));
            option.setQuestion(question); // Associer à la question
            answerOptions.add(option);
        }
        question.setAnswerOptions(answerOptions);

        // Récupérer points et ordre
        Integer points = (Integer) requestBody.get("points");
        Integer ordre = (Integer) requestBody.get("ordre");

        return questionService.ajouterQuestionAuTest(testId, question, points, ordre);
    }

}


//
//import com.cni.plateformetesttechnique.model.Question;
//import com.cni.plateformetesttechnique.service.QuestionService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/questions")
//@CrossOrigin(origins = "*")
//public class QuestionController {
//
//    private final QuestionService questionService;
//
//    public QuestionController(QuestionService questionService) {
//        this.questionService = questionService;
//    }
//
//
//    @PostMapping
//    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
//        return ResponseEntity.ok(questionService.addQuestion(question));
//    }
//
//
//    @GetMapping
//    public ResponseEntity<List<Question>> getAllQuestions() {
//        return ResponseEntity.ok(questionService.getAllQuestions());
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long id) {
//        Optional<Question> question = questionService.getQuestionById(id);
//        return question.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Question> updateQuestion(@PathVariable("id") Long id, @RequestBody Question questionDetails) {
//        try {
//            Question updatedQuestion = questionService.updateQuestion(id, questionDetails);
//            return ResponseEntity.ok(updatedQuestion);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
//        questionService.deleteQuestion(id);
//        return ResponseEntity.noContent().build();
//    }
//

//    @PostMapping("/{id}/verifier")
//    public ResponseEntity<Boolean> verifierReponse(@PathVariable ("id")  Long id, @RequestBody String reponseUtilisateur) {
//        boolean estCorrecte = questionService.verifierReponse(id, reponseUtilisateur);
//        return ResponseEntity.ok(estCorrecte);
//    }

//    @PostMapping("/{id}/verifier")
//    public ResponseEntity<Map<String, String>> verifierReponse(@PathVariable("id") Long id, @RequestBody String reponseUtilisateur) {
//        String message = questionService.verifierReponse(id, reponseUtilisateur);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", message);
//
//        return ResponseEntity.ok(response);
//    }
//
//
//}



//    @PostMapping("/addWithTest")
//    public ResponseEntity<?> addQuestionWithTest(@RequestBody AddQuestionRequest addQuestionRequest) {
//        // Vérifier si le test existe
//        Test test = testService.getTestById(addQuestionRequest.getTestId()).orElse(null);
//        if (test == null) {
//            return ResponseEntity.status(404).body("Test non trouvé avec l'ID : " + addQuestionRequest.getTestId());
//        }
//
//        // Convertir les Strings en enum
//        TypeQuestion type = TypeQuestion.valueOf(addQuestionRequest.getType()); // Conversion de String en TypeQuestion
//        NiveauQuestion niveau = NiveauQuestion.valueOf(addQuestionRequest.getNiveau()); // Conversion de String en NiveauQuestion
//
//        // Appel au service pour ajouter la question
//        Question newQuestion = questionService.addQuestionWithTest(addQuestionRequest, type, niveau);
//
//        // Associer la question au test via TestQuestion
//        TestQuestion testQuestion = new TestQuestion();
//        testQuestion.setTest(test);
//        testQuestion.setQuestion(newQuestion);
//        testQuestion.setPoints(addQuestionRequest.getPoints());
//        testQuestion.setOrdre(addQuestionRequest.getOrdre());
//
//        testQuestionService.addTestQuestion(testQuestion);
//
//        return ResponseEntity.ok("Question ajoutée et associée au test avec succès !");
//    }


