package com.cni.plateformetesttechnique.controller;

import com.cni.plateformetesttechnique.model.Test;
import com.cni.plateformetesttechnique.model.TestQuestion;
import com.cni.plateformetesttechnique.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/create/{adminId}")
    public ResponseEntity<Test> createTest(@RequestBody Test test, @PathVariable Long adminId) {
        Test createdTest = testService.createTest(test, adminId);
        return ResponseEntity.ok(createdTest);
    }
    // 2️⃣ Modifier un test (seulement en brouillon)
    @PutMapping("/{testId}")
    public ResponseEntity<Test> updateTest(@PathVariable Long testId, @RequestBody Test updatedTest) {
        Test updated = testService.updateTest(testId, updatedTest);
        return ResponseEntity.ok(updated);
    }
    @PostMapping("/{testId}/questions")
    public ResponseEntity<List<TestQuestion>> addQuestionsToTest(@PathVariable Long testId, @RequestBody List<TestQuestion> testQuestions) {
        List<TestQuestion> addedQuestions = testService.addQuestionsToTest(testId, testQuestions);
        return ResponseEntity.ok(addedQuestions);
    }
}
