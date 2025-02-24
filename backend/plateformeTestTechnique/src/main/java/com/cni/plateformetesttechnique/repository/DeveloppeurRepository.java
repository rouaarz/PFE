package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.Developpeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DeveloppeurRepository extends JpaRepository<Developpeur, Long> {

    // Récupérer un développeur par son email
    Optional<Developpeur> findByEmail(String email);
    Optional<Developpeur> findById(Long id);

}
