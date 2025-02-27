package com.cni.plateformetesttechnique.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cni.plateformetesttechnique.model.User;
import com.cni.plateformetesttechnique.repository.UserRepository;
import com.cni.plateformetesttechnique.response.MessageResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateUser(@RequestParam(name = "email") String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found with email: " + email));
        }

        User user = optionalUser.get();

        if (user.getActive()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is already active!"));
        }

        user.setActive(true);
        userRepository.save(user);

        sendConfirmationEmail(user);

        return ResponseEntity.ok(new MessageResponse("User activated successfully!"));
    }

    private void sendConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Votre compte a été activé");
        message.setText("Bonjour " + user.getUsername() + ",\n\n" +
                "Votre compte a été activé avec succès ! Vous pouvez maintenant vous connecter.\n\n" +
                "Cordialement,\nL'équipe.");

        try {
            mailSender.send(message);
            System.out.println("✅ Email d'activation envoyé à " + user.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi de l'email d'activation : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
