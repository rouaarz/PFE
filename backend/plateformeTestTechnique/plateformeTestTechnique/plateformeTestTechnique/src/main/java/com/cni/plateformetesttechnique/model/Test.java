package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tests")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private Integer duree; // En minutes (NULL = illimité)

    @Column(nullable = false)
    private String type; // QCM, Algo, Mixte...

    private Boolean accesPublic; // true = ouvert à tous, false = sur invitation
    private Integer limiteTentatives; // NULL = illimité

    @Column(nullable = false)
    private String statut; // BROUILLON, PUBLIE, ARCHIVE

    private LocalDateTime dateCreation;
    private LocalDateTime dateExpiration; // NULL = pas de date limite

    @ManyToOne
    @JoinColumn(name = "id_administrateur", nullable = false)
    private Administrateur administrateur;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestQuestion> testQuestions;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAccesPublic() {
        return accesPublic;
    }

    public void setAccesPublic(Boolean accesPublic) {
        this.accesPublic = accesPublic;
    }

    public Integer getLimiteTentatives() {
        return limiteTentatives;
    }

    public void setLimiteTentatives(Integer limiteTentatives) {
        this.limiteTentatives = limiteTentatives;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public List<TestQuestion> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(List<TestQuestion> testQuestions) {
        this.testQuestions = testQuestions;
    }
}
