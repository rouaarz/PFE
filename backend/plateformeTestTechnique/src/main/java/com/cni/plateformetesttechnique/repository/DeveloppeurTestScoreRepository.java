package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.Developpeur;
import com.cni.plateformetesttechnique.model.DeveloppeurTestScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloppeurTestScoreRepository extends JpaRepository<DeveloppeurTestScore, Long> {
    List<DeveloppeurTestScore> findByDeveloppeur(Developpeur developpeur);
}

