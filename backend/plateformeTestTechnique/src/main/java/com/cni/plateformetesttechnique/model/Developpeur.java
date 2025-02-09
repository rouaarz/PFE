package com.cni.plateformetesttechnique.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Developpeur extends User {

    private String specialite;
    
    @ElementCollection
    @CollectionTable(name = "developpeur_technologies", joinColumns = @JoinColumn(name = "developpeur_id"))
    @Column(name = "technologie")
    private List<String> technologies;

    private int experience;

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
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

	public Developpeur(Long id, String email, String motDePasse, String role, String specialite,
			List<String> technologies, int experience) {
		super(id, email, motDePasse, role);
		this.specialite = specialite;
		this.technologies = technologies;
		this.experience = experience;
	}

	public Developpeur(Long id, String email, String motDePasse, String role) {
		super(id, email, motDePasse, role);
	} 

    
}