package com.cni.plateformetesttechnique.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class DeveloppeurResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    @JsonIgnore

    private Test test; // Le test auquel le développeur répond

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnore

    private Question question; // La question à laquelle le développeur répond

    @ManyToMany
    @JoinTable(
            name = "Developpeur_answer_options",
            joinColumns = @JoinColumn(name = "Developpeur_response_id"),
            inverseJoinColumns = @JoinColumn(name = "answer_option_id")
    )
    private List<AnswerOption> selectedAnswerOptions; // Options sélectionnées par le développeur

    private Boolean isCorrect; // True si toutes les options sélectionnées sont correctes

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "developpeur_id", nullable = false)
    @JsonIgnore
    private Developpeur developpeur;
 // Le développeur qui a répondu à la question


    // Constructeur par défaut
    public DeveloppeurResponse() {}

    // Constructeur avec paramètres
    public DeveloppeurResponse(Test test, Question question, List<AnswerOption> selectedAnswerOptions, Boolean isCorrect, Developpeur developpeur) {
        this.test = test;
        this.question = question;
        this.selectedAnswerOptions = selectedAnswerOptions;
        this.isCorrect = isCorrect;
        this.developpeur = developpeur;
    }

    // Getters et Setters
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

    public List<AnswerOption> getSelectedAnswerOptions() {
        return selectedAnswerOptions;
    }

    public void setSelectedAnswerOptions(List<AnswerOption> selectedAnswerOptions) {
        this.selectedAnswerOptions = selectedAnswerOptions;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Developpeur getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(Developpeur Developpeur) {
        this.developpeur = developpeur;
    }
}
