package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation_tests")
@Builder

public class InvitationTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "developpeur_id", nullable = false)
    private Developpeur developpeur;

    @Column(nullable = false)
    private String statut; // "PENDING", "ACCEPTED", "DECLINED"

    private LocalDateTime dateInvitation;

    // Constructors
    public InvitationTest() {}

    public InvitationTest(Long id, Test test, Developpeur developpeur, String statut, LocalDateTime dateInvitation) {
        this.id = id;
        this.test = test;
        this.developpeur = developpeur;
        this.statut = statut;
        this.dateInvitation = dateInvitation;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Developpeur getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(Developpeur developpeur) {
        this.developpeur = developpeur;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateInvitation() {
        return dateInvitation;
    }

    public void setDateInvitation(LocalDateTime dateInvitation) {
        this.dateInvitation = dateInvitation;
    }
}
