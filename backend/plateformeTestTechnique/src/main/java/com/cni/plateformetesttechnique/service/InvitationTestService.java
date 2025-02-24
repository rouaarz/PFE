package com.cni.plateformetesttechnique.service;

import com.cni.plateformetesttechnique.model.Developpeur;
import com.cni.plateformetesttechnique.model.InvitationTest;
import com.cni.plateformetesttechnique.model.Test;
import com.cni.plateformetesttechnique.repository.DeveloppeurRepository;
import com.cni.plateformetesttechnique.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.cni.plateformetesttechnique.repository.InvitationTestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class InvitationTestService {
    @Autowired
    private InvitationTestRepository invitationTestRepository;
    @Autowired
    private DeveloppeurRepository developpeurRepository; // ✅ Ajout de l’injection du repository
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private EmailService emailService;
    public void sendInvitationEmails(Test test) {
        List<InvitationTest> invitations = invitationTestRepository.findByTest(test);

        for (InvitationTest invitation : invitations) {
            Developpeur developer = invitation.getDeveloppeur();
            emailService.sendTestPublishedNotification(test, developer);
        }
    }
    public void inviteDevelopers(Long testId, List<Long> developerIds) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test non trouvé"));

        if (!"PUBLIE".equals(test.getStatut())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vous devez publier le test avant d'inviter des développeurs");
        }

        for (Long devId : developerIds) {
            Developpeur developer = developpeurRepository.findById(devId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Développeur non trouvé"));
            System.out.println("Développeur trouvé : " + developer);

            boolean alreadyInvited = invitationTestRepository.findByTestAndDeveloppeur(test, developer).isPresent();
            if (alreadyInvited) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le développeur ID " + devId + " est déjà invité");
            }

            InvitationTest invitation = new InvitationTest();
            invitation.setTest(test);
            invitation.setDeveloppeur(developer);
            invitation.setStatut("PENDING");
            invitation.setDateInvitation(LocalDateTime.now());

            invitationTestRepository.save(invitation);

            System.out.println(" Invitation enregistrée pour : " + developer.getEmail()); // ✅ Debug

            //  Vérifier si l'email est bien appelé ici
            emailService.sendInvitationEmail(invitation);
            System.out.println(" Email envoyé à : " + developer.getEmail()); // ✅ Debug
        }
    }

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