package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;

@Entity
@Table(name = "test_questions")
public class TestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_test", nullable = false)
    private Test test;

    @ManyToOne
    @JoinColumn(name = "id_question", nullable = false)
    private Question question;

    private Integer points; // Nombre de points attribués pour cette question
    private Integer ordre; // Position de la question dans le test

    // Default constructor
    public TestQuestion() {
    }

    // Constructor with parameters
    public TestQuestion(Test test, Question question, Integer points, Integer ordre) {
        this.test = test;
        this.question = question;
        this.points = points;
        this.ordre = ordre;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
}
