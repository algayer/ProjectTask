package com.example.common.model;

import java.io.Serializable;
import java.util.List;

public class Equipe implements Serializable{

    private static final long serialVersionUID = 1L;

    private int ID_Equipe;
    private String NomeEquipe;
    private List<Pessoa> membros;

    public Equipe() {
        // Construtor vazio é necessário para serialização
    }

    public Equipe(int ID_Equipe, String NomeEquipe) {
        this.ID_Equipe = ID_Equipe;
        this.NomeEquipe = NomeEquipe;
    }

    public Equipe(int ID_Equipe, String NomeEquipe, List<Pessoa> membros) {
        this.ID_Equipe = ID_Equipe;
        this.NomeEquipe = NomeEquipe;
        this.membros = membros;
    }

    public Equipe(int ID_Equipe) {
        this.ID_Equipe = ID_Equipe;
    }

    public Equipe(String NomeEquipe) {
        this.NomeEquipe = NomeEquipe;
    }

    public Equipe(String NomeEquipe, List<Pessoa> membros) {
        this.NomeEquipe = NomeEquipe;
        this.membros = membros;
    }

    // Getters e Setters
    public int getID_Equipe() {
        return ID_Equipe;
    }

    public void setID_Equipe(int ID_Equipe) {
        this.ID_Equipe = ID_Equipe;
    }

    public String getNome() {
        return NomeEquipe;
    }

    public void setNome(String NomeEquipe) {
        this.NomeEquipe = NomeEquipe;
    }

    public List<Pessoa> getMembros() {
        return membros;
    }

    public void setMembros(List<Pessoa> membros) {
        this.membros = membros;
    }

    @Override
    public String toString() {
        return "Equipe{"
                + "ID_Equipe=" + ID_Equipe
                + ", Nome='" + NomeEquipe + '\''
                + ", Membros=" + membros
                + '}';
    }
}
