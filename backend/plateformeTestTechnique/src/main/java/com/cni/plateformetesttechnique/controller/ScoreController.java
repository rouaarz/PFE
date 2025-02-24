package com.cni.plateformetesttechnique.controller;


import com.cni.plateformetesttechnique.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/calculer/{testId}/{developpeurId}")
    public ResponseEntity<Map<String, Object>> calculerScore(
            @PathVariable Long testId, @PathVariable Long developpeurId) {

        try {
            // Calculer le score
            Double score = scoreService.calculerScore(testId, developpeurId);

            // Construire la réponse JSON
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("score", score);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // En cas d'erreur, retourner un message d'erreur
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}

