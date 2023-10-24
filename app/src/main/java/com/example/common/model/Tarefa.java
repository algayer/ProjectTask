package com.example.common.model;

import java.io.Serializable;
import java.sql.Time;

public class Tarefa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID_Tarefa;
    private String Nome;
    private String Descricao;
    private Time HorasTrabalhadas;
    private int ID_Projeto;
    private int ID_Pessoa;

    public Tarefa() {
        // Construtor vazio é necessário para serialização
    }

    public Tarefa(int ID_Tarefa, String Nome, String Descricao, Time HorasTrabalhadas, int ID_Projeto, int ID_Pessoa) {
        this.ID_Tarefa = ID_Tarefa;
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.HorasTrabalhadas = HorasTrabalhadas;
        this.ID_Projeto = ID_Projeto;
        this.ID_Pessoa = ID_Pessoa;
    }

    // Getters e Setters
    public int getID_Tarefa() {
        return ID_Tarefa;
    }

    public void setID_Tarefa(int ID_Tarefa) {
        this.ID_Tarefa = ID_Tarefa;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public Time getHorasTrabalhadas() {
        return HorasTrabalhadas;
    }

    public void setHorasTrabalhadas(Time HorasTrabalhadas) {
        this.HorasTrabalhadas = HorasTrabalhadas;
    }

    public int getID_Projeto() {
        return ID_Projeto;
    }

    public void setID_Projeto(int ID_Projeto) {
        this.ID_Projeto = ID_Projeto;
    }

    public int getID_Pessoa() {
        return ID_Pessoa;
    }

    public void setID_Pessoa(int ID_Pessoa) {
        this.ID_Pessoa = ID_Pessoa;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "ID_Tarefa=" + ID_Tarefa +
                ", Nome='" + Nome + '\'' +
                ", Descricao='" + Descricao + '\'' +
                ", HorasTrabalhadas=" + HorasTrabalhadas +
                ", ID_Projeto=" + ID_Projeto +
                ", ID_Pessoa=" + ID_Pessoa +
                '}';
    }
}

