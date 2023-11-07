package com.example.common.model;

import java.io.Serializable;
import java.util.List;

public class Equipe implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID_Equipe;
    private String Nome;
    private List<Pessoa> membros;

    public Equipe() {
        // Construtor vazio é necessário para serialização
    }

    public Equipe(int ID_Equipe, String Nome) {
        this.ID_Equipe = ID_Equipe;
        this.Nome = Nome;
    }

    public Equipe(int ID_Equipe, String nome, List<Pessoa> membros) {
        this.ID_Equipe = ID_Equipe;
        this.Nome = nome;
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
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public List<Pessoa> getMembros() {
        return membros;
    }

    public void setMembros(List<Pessoa> membros) {
        this.membros = membros;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "ID_Equipe=" + ID_Equipe +
                ", Nome='" + Nome + '\'' +
                ", Membros=" + membros +
                '}';
    }
}
