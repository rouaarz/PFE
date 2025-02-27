package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.Reponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReponseService {

    private final ReponseRepository reponseRepository;

    @Autowired
    public ReponseService(ReponseRepository reponseRepository) {
        this.reponseRepository = reponseRepository;
    }

    public List<Reponse> getAllReponses() {
        return reponseRepository.findAll();
    }

    public Reponse getReponseById(Long id) {
       
        return reponseRepository.findById(id).orElse(null);
    }

    public Reponse createReponse(Reponse reponse) {
       
        reponse.setDateSoumission(LocalDateTime.now());
        return reponseRepository.save(reponse);
    }

    public Reponse updateReponse(Long id, Reponse reponseDetails) {
        Reponse existingReponse = getReponseById(id);
        if (existingReponse == null) {
            
            return null;
        }
        existingReponse.setContenu(reponseDetails.getContenu());
        existingReponse.setEstCorrecte(reponseDetails.isEstCorrecte());
        existingReponse.setDeveloppeur(reponseDetails.getDeveloppeur());
        existingReponse.setQuestion(reponseDetails.getQuestion());
        return reponseRepository.save(existingReponse);
    }

    public void deleteReponse(Long id) {
        if (getReponseById(id) != null) {
            reponseRepository.deleteById(id);
        }
    }
}
