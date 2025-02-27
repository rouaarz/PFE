package com.cni.plateformetesttechnique.repository;
import com.cni.plateformetesttechnique.model.Developpeur;
import com.cni.plateformetesttechnique.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cni.plateformetesttechnique.model.InvitationTest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationTestRepository extends JpaRepository<InvitationTest, Long> {

    // Récupérer toutes les invitations pour un test donné
    List<InvitationTest> findByTest(Test test);
    Optional<InvitationTest> findByTestAndDeveloppeur(Test test, Developpeur developpeur); // ✅ Ajout de la méthode

}


