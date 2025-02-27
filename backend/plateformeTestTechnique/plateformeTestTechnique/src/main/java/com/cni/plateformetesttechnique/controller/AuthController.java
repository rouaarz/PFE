package com.cni.plateformetesttechnique.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cni.plateformetesttechnique.model.ERole;
import com.cni.plateformetesttechnique.model.Role;
import com.cni.plateformetesttechnique.model.User;
import com.cni.plateformetesttechnique.repository.RoleRepository;
import com.cni.plateformetesttechnique.repository.UserRepository;
import com.cni.plateformetesttechnique.request.LoginRequest;
import com.cni.plateformetesttechnique.request.SignupRequest;
import com.cni.plateformetesttechnique.response.MessageResponse;
import com.cni.plateformetesttechnique.security.services.UserDetailsImpl;
import com.cni.plateformetesttechnique.security.jwt.JwtUtils;
import com.cni.plateformetesttechnique.response.JwtResponse;

import org.springframework.mail.javamail.JavaMailSender;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur introuvable.");
        }

        User user = userOptional.get();

        // Vérifier si l'utilisateur est actif
        if (!user.getActive()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Votre compte n'est pas encore activé.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Création d'un nouvel utilisateur
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role roleDeveloppeur = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
                .orElseThrow(() -> new RuntimeException("Error: Role DEVELOPPEUR not found."));

        if (strRoles == null || strRoles.isEmpty()) {
            // Rôle par défaut
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(userRole);
        } else {
            for (String role : strRoles) {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(adminRole);
                        break;
                    case "developpeur":
                        roles.add(roleDeveloppeur);
                        user.setActive(false); // Développeur inactif en attente d'activation
                        //String activationToken = UUID.randomUUID().toString();
                        //user.setActivationToken(activationToken);
                       // sendActivationEmail(user, activationToken);
                        sendActivationEmail(user);
                        
                        
                        break;
                    case "chefprojet":
                        Role chefProjetRole = roleRepository.findByName(ERole.ROLE_CHEF_PROJET)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(chefProjetRole);
                        break;
                    default:
                        return ResponseEntity.badRequest().body(new MessageResponse("Error: Role " + role + " not recognized."));
                }
            }
        }

        // Activer l'utilisateur s'il a au moins un rôle attribué (sauf pour les développeurs)
        if (!roles.isEmpty() && !roles.contains(roleDeveloppeur)) {
            user.setActive(true);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }

   /* private void sendActivationEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Activation de votre compte");
        message.setText("Bonjour " + user.getUsername() + ",\n\n" +
                "Merci de vous être inscrit. Un administrateur doit activer votre compte.\n\n" +
                //"Token d'activation : " + token + "\n\n" +
                "Cordialement,\nL'équipe.");

        mailSender.send(message);
    }*/
    private void sendActivationEmail(User user) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("Activation de votre compte");
    message.setText("Bonjour " + user.getUsername() + ",\n\n" +
            "Merci de vous être inscrit. Un administrateur doit activer votre compte.\n\n" +
            "Cordialement,\nL'équipe.");

    mailSender.send(message);
}
    
}
