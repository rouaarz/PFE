package com.cni.plateformetesttechnique.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cni.plateformetesttechnique.model.Reponse;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, Long> {
}
