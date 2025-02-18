package com.cni.plateformetesttechnique.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "administrateurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrateur extends User {

    private String grade; // Ex: "Responsable RH", "Tech Lead"

    @OneToMany(mappedBy = "administrateur", cascade = CascadeType.ALL)
    private List<Test> testsCrees;
}
