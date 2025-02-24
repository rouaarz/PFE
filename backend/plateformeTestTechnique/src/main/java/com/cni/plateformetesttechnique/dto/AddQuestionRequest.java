//package com.cni.plateformetesttechnique.dto;
//
//import java.util.List;
//
//public class AddQuestionRequest {
//
//    private Long testId;            // ID du test auquel la question sera ajoutée
//    private String type;            // Type de la question (à convertir en TypeQuestion)
//    private String enonce;          // L'énoncé de la question
//    private List<String> reponses;  // Liste des réponses possibles
//    private String niveau;          // Niveau de la question (à convertir en NiveauQuestion)
//    private Integer points;         // Nombre de points pour la question
//    private Integer ordre;          // Position de la question dans le test
//    private List<Integer> indexReponsesCorrectes;  // Liste des indices des réponses correctes
//
//    // Getters et Setters
//
//    public Long getTestId() {
//        return testId;
//    }
//
//    public void setTestId(Long testId) {
//        this.testId = testId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getEnonce() {
//        return enonce;
//    }
//
//    public void setEnonce(String enonce) {
//        this.enonce = enonce;
//    }
//
//    public List<String> getReponses() {
//        return reponses;
//    }
//
//    public void setReponses(List<String> reponses) {
//        this.reponses = reponses;
//    }
//
//    public String getNiveau() {
//        return niveau;
//    }
//
//    public void setNiveau(String niveau) {
//        this.niveau = niveau;
//    }
//
//    public Integer getPoints() {
//        return points;
//    }
//
//    public void setPoints(Integer points) {
//        this.points = points;
//    }
//
//    public Integer getOrdre() {
//        return ordre;
//    }
//
//    public void setOrdre(Integer ordre) {
//        this.ordre = ordre;
//    }
//
//    public List<Integer> getIndexReponsesCorrectes() {
//        return indexReponsesCorrectes;
//    }
//
//    public void setIndexReponsesCorrectes(List<Integer> indexReponsesCorrectes) {
//        this.indexReponsesCorrectes = indexReponsesCorrectes;
//    }
//}
package com.cni.plateformetesttechnique.dto;

import java.util.List;

public class AddQuestionRequest {

    private String enonce; // L'énoncé de la question
    private List<String> reponses; // Liste des réponses possibles
    private List<Integer> indexReponsesCorrectes; // Indices des réponses correctes

    // Getters et Setters
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

    public List<Integer> getIndexReponsesCorrectes() {
        return indexReponsesCorrectes;
    }

    public void setIndexReponsesCorrectes(List<Integer> indexReponsesCorrectes) {
        this.indexReponsesCorrectes = indexReponsesCorrectes;
    }
}
