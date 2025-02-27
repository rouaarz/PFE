package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Question  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeQuestion type;

    private String enonce;

    @ElementCollection
    @CollectionTable(name = "question_reponses", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "reponse")
    private List<String> reponses;
    
    @Enumerated(EnumType.STRING)
    private NiveauQuestion niveau;
    
    private String reponseCorrecte;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<TestQuestion> testQuestions;
	
	public long  getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeQuestion getType() {
		return type;
	}

	public void setType(TypeQuestion type) {
		this.type = type;
	}

	public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	public List<String> getReponses() {
		return reponses;
	}

	public void setReponses(List<String> reponses) {
		this.reponses = reponses;
	}

	public NiveauQuestion getNiveau() {
		return niveau;
	}

	public void setNiveau(NiveauQuestion niveau) {
		this.niveau = niveau;

}

	public Question(Long id, TypeQuestion type, String enonce, List<String> reponses, NiveauQuestion niveau,
			String reponseCorrecte, List<TestQuestion> testQuestions) {
		super();
		this.id = id;
		this.type = type;
		this.enonce = enonce;
		this.reponses = reponses;
		this.niveau = niveau;
		this.reponseCorrecte = reponseCorrecte;
		this.testQuestions = testQuestions;
	}
	
	public Question() {}

	public String getReponseCorrecte() {
		return reponseCorrecte;
	}

	public void setReponseCorrecte(String reponseCorrecte) {
		this.reponseCorrecte = reponseCorrecte;
	}

	public List<TestQuestion> getTestQuestions() {
		return testQuestions;
	}

	public void setTestQuestions(List<TestQuestion> testQuestions) {
		this.testQuestions = testQuestions;
	}
	

}
	 



