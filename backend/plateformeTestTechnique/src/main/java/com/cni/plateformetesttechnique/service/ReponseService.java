//package com.cni.plateformetesttechnique.service;
//
//import com.cni.plateformetesttechnique.model.Developpeur;
//import com.cni.plateformetesttechnique.model.Question;
//import com.cni.plateformetesttechnique.model.Reponse;
//import com.cni.plateformetesttechnique.repository.DeveloppeurRepository;
//import com.cni.plateformetesttechnique.repository.QuestionRepository;
//import com.cni.plateformetesttechnique.repository.ReponseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class ReponseService {
//
//    private final ReponseRepository reponseRepository;
//    private final DeveloppeurRepository developpeurRepository;
//    private final QuestionRepository questionRepository;
//    @Autowired
//    public ReponseService(ReponseRepository reponseRepository,DeveloppeurRepository developpeurRepository, QuestionRepository questionRepository) {
//        this.reponseRepository = reponseRepository;
//        this.developpeurRepository = developpeurRepository;
//        this.questionRepository = questionRepository;
//    }
//
//    public List<Reponse> getAllReponses() {
//        return reponseRepository.findAll();
//    }
//
//    public Reponse getReponseById(Long id) {
//
//        return reponseRepository.findById(id).orElse(null);
//    }
//    public Reponse createReponse(Reponse reponse) {
//        // Récupérer le developpeur et la question à partir de leurs ID
//        Developpeur developpeur = developpeurRepository.findById(reponse.getDeveloppeur().getId()).orElse(null);
//        Question question = questionRepository.findById(reponse.getQuestion().getId()).orElse(null);
//
//        // Vérifier si les entités existent
//        if (developpeur == null || question == null) {
//            throw new IllegalArgumentException("Le développeur ou la question n'existe pas.");
//        }
//
//        // Assigner les objets récupérés à la réponse
//        reponse.setDeveloppeur(developpeur);
//        reponse.setQuestion(question);
//
//        // Vérifier si la réponse soumise est correcte
//        boolean estCorrecte = question.getReponseCorrecte() != null
//                && question.getReponseCorrecte().trim().equalsIgnoreCase(reponse.getContenu().trim());
//
//        reponse.setEstCorrecte(estCorrecte);
//
//        // Définir la date de soumission avant de sauvegarder
//        reponse.setDateSoumission(LocalDateTime.now());
//
//        // Sauvegarder et retourner la réponse
//        return reponseRepository.save(reponse);
//    }


//    public Reponse createReponse(Reponse reponse) {
//        // Récupérer le developpeur et la question à partir de leurs ID
//        Developpeur developpeur = developpeurRepository.findById(reponse.getDeveloppeur().getId()).orElse(null);
//        Question question = questionRepository.findById(reponse.getQuestion().getId()).orElse(null);
//
//        // Si l'un des objets est null, retourner null ou gérer l'erreur comme tu préfères
//        if (developpeur == null || question == null) {
//            return null; // ou tu peux lancer une exception si tu préfères
//        }
//
//        // Assigner les objets récupérés à la réponse
//        reponse.setDeveloppeur(developpeur);
//        reponse.setQuestion(question);
//
//        // Définir la date de soumission avant de sauvegarder
//        reponse.setDateSoumission(LocalDateTime.now());
//
//        return reponseRepository.save(reponse);
//    }

//    public Reponse updateReponse(Long id, Reponse reponseDetails) {
//        Reponse existingReponse = getReponseById(id);
//        if (existingReponse == null) {
//
//            return null;
//        }
//        existingReponse.setContenu(reponseDetails.getContenu());
//        existingReponse.setEstCorrecte(reponseDetails.isEstCorrecte());
//        existingReponse.setDeveloppeur(reponseDetails.getDeveloppeur());
//        existingReponse.setQuestion(reponseDetails.getQuestion());
//        return reponseRepository.save(existingReponse);
//    }
//
//    public void deleteReponse(Long id) {
//        if (getReponseById(id) != null) {
//            reponseRepository.deleteById(id);
//        }
//    }
//}