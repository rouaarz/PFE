package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findByStatut(String statut); // Récupérer les tests par statut (BROUILLON, PUBLIE)

    List<Test> findByAdministrateurId(Long adminId); // Récupérer tous les tests créés par un administrateur
}
