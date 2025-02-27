package com.cni.plateformetesttechnique.model;
import jakarta.persistence.*;

@Entity
public class DeveloppeurTestScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "developpeur_id", nullable = false)
    private Developpeur developpeur;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    private Double score;  // Score final du développeur pour ce test

    public DeveloppeurTestScore() {}

    public DeveloppeurTestScore(Developpeur developpeur, Test test, Double score) {
        this.developpeur = developpeur;
        this.test = test;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Developpeur getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(Developpeur developpeur) {
        this.developpeur = developpeur;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DeveloppeurTestScore{" +
                "id=" + id +
                ", developpeur=" + developpeur +
                ", test=" + test +
                ", score=" + score +
                '}';
    }
}
