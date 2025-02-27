package com.cni.plateformetesttechnique.controller;

import com.cni.plateformetesttechnique.model.Reponse;
import com.cni.plateformetesttechnique.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reponses")
@CrossOrigin("*") 
@Validated
public class ReponseController {

    private final ReponseService reponseService;

    @Autowired
    public ReponseController(ReponseService reponseService) {
        this.reponseService = reponseService;
    }

    @GetMapping
    public ResponseEntity<List<Reponse>> getAllReponses() {
        List<Reponse> reponses = reponseService.getAllReponses();
        return ResponseEntity.ok(reponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reponse> getReponseById(@PathVariable Long id) {
        Reponse reponse = reponseService.getReponseById(id);
        return ResponseEntity.ok(reponse);
    }

    @PostMapping
    public ResponseEntity<Reponse> createReponse(@RequestBody Reponse reponse) {
        Reponse newReponse = reponseService.createReponse(reponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reponse> updateReponse(@PathVariable Long id, @RequestBody Reponse reponseDetails) {
        Reponse updatedReponse = reponseService.updateReponse(id, reponseDetails);
        return ResponseEntity.ok(updatedReponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReponse(@PathVariable Long id) {
        reponseService.deleteReponse(id);
        return ResponseEntity.noContent().build();
    }
}
