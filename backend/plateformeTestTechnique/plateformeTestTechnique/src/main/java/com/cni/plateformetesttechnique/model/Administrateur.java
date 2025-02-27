package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
public class Administrateur extends User {

    private String grade;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}


	public Administrateur() {
		super();
	} 
	
	public Administrateur(String username, String email, String password, String grade) {
        super(username, email, password); // Appel au constructeur de la classe parente User
        this.grade = grade;
    }

    
    


}
