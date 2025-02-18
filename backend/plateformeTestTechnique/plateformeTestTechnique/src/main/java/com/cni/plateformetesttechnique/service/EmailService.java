package com.cni.plateformetesttechnique.service;


import com.cni.plateformetesttechnique.model.Developpeur;
import com.cni.plateformetesttechnique.model.InvitationTest;
import com.cni.plateformetesttechnique.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTestPublishedNotification(Test test, Developpeur developer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("mahanouri2022@gmail.com"); // ✅ Email temporaire pour le test
        message.setSubject("Invitation à passer un test publié !");
        message.setText("Bonjour "+ ",\n\n"
                + "Le test '" + test.getTitre() + "' auquel vous avez été invité est maintenant disponible.\n"
                + "Vous pouvez y accéder et commencer dès maintenant :\n\n"
                + "http://localhost:4200/tests/" + test.getId() + "/questions\n\n"
                + "Bonne chance !");

        mailSender.send(message);
    }
    public void sendTestAccessEmail(InvitationTest invitation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("mahanouri2022@gmail.com"); // ✅ Email temporaire pour le test
        message.setSubject("Accès à votre test");
        message.setText("Bonjour " + ",\n\n"
                + "Vous avez accepté l'invitation au test : " + invitation.getTest().getTitre() + ".\n"
                + "Vous pouvez y accéder et commencer immédiatement :\n\n"
                + "http://localhost:4200/tests/" + invitation.getTest().getId() + "/questions\n\n"
                + "Bonne chance !");
        mailSender.send(message);
    }

    //////hedhi eli tastitha
//    public void sendInvitationEmail(InvitationTest invitation) {
//        System.out.println("📧 Tentative d'envoi d'email à : " + invitation.getDeveloppeur().getEmail()); // ✅ Debug
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("mahanouri2022@gmail.com"); // ✅ Email temporaire pour test
//        message.setSubject("Invitation à passer un test !");
//        message.setText("Bonjour,\n\n"
//                + "Vous avez été invité à passer le test : " + invitation.getTest().getTitre() + ".\n"
//                + "Cliquez sur le lien suivant pour accepter ou refuser l'invitation :\n\n"
//                + "http://localhost:4200/invitations/" + invitation.getId() + "\n\n"
//                + "Bonne chance !");
//
//        mailSender.send(message);
//        System.out.println("✅ Email envoyé avec succès !");
//    }
    public void sendInvitationEmail(InvitationTest invitation) {
        String developerEmail = invitation.getDeveloppeur().getEmail(); // ✅ Récupération dynamique de l'email

        System.out.println("📧 Tentative d'envoi d'email à : " + developerEmail); // ✅ Debug

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(developerEmail); // ✅ Utilisation de l'email du développeur
        message.setSubject("Invitation à passer un test !");
        message.setText("Bonjour " + ",\n\n"
                + "Vous avez été invité à passer le test : " + invitation.getTest().getTitre() + ".\n"
                + "Cliquez sur le lien suivant pour accepter ou refuser l'invitation :\n\n"
                + "http://localhost:4200/invitations/" + invitation.getId() + "\n\n"
                + "Bonne chance !");

        mailSender.send(message);
        System.out.println("✅ Email envoyé avec succès à " + developerEmail);
    }


}
