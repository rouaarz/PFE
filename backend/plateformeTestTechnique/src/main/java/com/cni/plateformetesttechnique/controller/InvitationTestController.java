package com.cni.plateformetesttechnique.controller;

import com.cni.plateformetesttechnique.service.InvitationTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitations")
public class InvitationTestController {

    @Autowired
    private InvitationTestService invitationTestService;

    @PutMapping("/{invitationId}/respond")
    public ResponseEntity<String> respondToInvitation(@PathVariable Long invitationId, @RequestParam boolean accept) {
        invitationTestService.respondToInvitation(invitationId, accept);
        return ResponseEntity.ok(accept ? "Invitation acceptée !" : "Invitation refusée !");
    }
    @PostMapping("/{testId}/invite")
    public ResponseEntity<String> inviteDevelopers(@PathVariable Long testId, @RequestBody List<Long> developerIds) {
        invitationTestService.inviteDevelopers(testId, developerIds);
        return ResponseEntity.ok("Invitations envoyées avec succès");
    }
}
