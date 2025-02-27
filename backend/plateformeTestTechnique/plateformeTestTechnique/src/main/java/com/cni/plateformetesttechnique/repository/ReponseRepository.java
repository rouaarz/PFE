package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, Long> {

    // Si tu veux ajouter des méthodes spécifiques, par exemple pour trouver des réponses par question
    List<Reponse> findByQuestionId(Long questionId);
}
