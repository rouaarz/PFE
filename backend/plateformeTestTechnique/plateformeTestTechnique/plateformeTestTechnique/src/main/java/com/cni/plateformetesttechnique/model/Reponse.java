package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;
    private boolean estCorrecte;
    private LocalDateTime dateSoumission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "developpeur_id", nullable = false)
    private Developpeur developpeur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public boolean isEstCorrecte() {
		return estCorrecte;
	}

	public void setEstCorrecte(boolean estCorrecte) {
		this.estCorrecte = estCorrecte;
	}

	public LocalDateTime getDateSoumission() {
		return dateSoumission;
	}

	public void setDateSoumission(LocalDateTime dateSoumission) {
		this.dateSoumission = dateSoumission;
	}

	public Developpeur getDeveloppeur() {
		return developpeur;
	}

	public void setDeveloppeur(Developpeur developpeur) {
		this.developpeur = developpeur;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Reponse(Long id, String contenu, boolean estCorrecte, LocalDateTime dateSoumission, Developpeur developpeur,
			Question question) {
		super();
		this.id = id;
		this.contenu = contenu;
		this.estCorrecte = estCorrecte;
		this.dateSoumission = dateSoumission;
		this.developpeur = developpeur;
		this.question = question;
	}

	public Reponse() {
		super();
	}
    

}