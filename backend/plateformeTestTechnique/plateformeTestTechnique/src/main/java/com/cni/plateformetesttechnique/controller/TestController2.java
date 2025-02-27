package com.cni.plateformetesttechnique.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController2 {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('Developpeur') or hasRole('ADMIN') or hasRole('ChefProjet')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/developpeur")
	@PreAuthorize("hasRole('Developpeur')")
	public String DeveloppeurAccess() {
		return "Developpeur Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	@GetMapping("/ChefProjet")
	@PreAuthorize("hasRole('ChefProjet')")
	public String ChefProjetAccess() {
		return "ChefProjet Board.";
	}
}
