package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.InvitationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.cni.plateformetesttechnique.repository.InvitationTestRepository;
@Service

public class InvitationTestService {
    @Autowired
    private InvitationTestRepository invitationTestRepository;
    @Autowired
    private EmailService emailService;
public void respondToInvitation(Long invitationId, boolean accept) {
    InvitationTest invitation = invitationTestRepository.findById(invitationId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitation non trouvée"));

    if (!"PENDING".equals(invitation.getStatut())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'invitation a déjà été traitée");
    }

    // Mise à jour du statut
    invitation.setStatut(accept ? "ACCEPTED" : "DECLINED");
    invitationTestRepository.save(invitation);

    if (accept) {
        emailService.sendTestAccessEmail(invitation);
    }
}

}