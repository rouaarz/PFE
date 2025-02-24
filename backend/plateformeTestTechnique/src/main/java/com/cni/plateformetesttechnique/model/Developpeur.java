package com.cni.plateformetesttechnique.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Developpeur extends User {

    private String specialite;
	private Double score;  // Score total du développeur


	@ElementCollection
    @CollectionTable(name = "developpeur_technologies", joinColumns = @JoinColumn(name = "developpeur_id"))
    @Column(name = "technologie")
    private List<String> technologies;

    private int experience;
	@OneToMany(mappedBy = "developpeur")
	private List<DeveloppeurResponse> developpeurResponses;
	@OneToMany(mappedBy = "developpeur", cascade = CascadeType.ALL)
	private List<InvitationTest> invitations;
	public Developpeur() {
		super(); // ✅ Appelle le constructeur de `User`
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	public List<String> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<String> technologies) {
		this.technologies = technologies;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public Developpeur(Long id, String email, String motDePasse, String role, String specialite,Double score,
			List<String> technologies, int experience) {
		super(id, email, motDePasse, role);
		this.specialite = specialite;
		this.score = score;
		this.technologies = technologies;
		this.experience = experience;
	}

	public Developpeur(Long id, String email, String motDePasse, String role) {
		super(id, email, motDePasse, role);
	} 

    
}