package com.example.common.model;

import java.io.Serializable;
import java.util.Date;

public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID_Projeto;

    private int ID_Pessoa;
    private String Nome;
    private String Descricao;
    private Date DataEntrega;
    private Date DataInicial;
    private int ID_Equipe;

    public Projeto() {
        // Construtor vazio é necessário para serialização
    }

    public Projeto(int ID_Projeto, int ID_Pessoa, String Nome, String Descricao, Date DataEntrega, Date DataInicial, int ID_Equipe) {
        this.ID_Projeto = ID_Projeto;
        this.ID_Pessoa = ID_Pessoa;
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.DataEntrega = DataEntrega;
        this.DataInicial = DataInicial;
        this.ID_Equipe = ID_Equipe;
    }

    // Getters e Setters
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

    public Date getDataEntrega() {
        return DataEntrega;
    }

    public void setDataEntrega(Date DataEntrega) {
        this.DataEntrega = DataEntrega;
    }

    public Date getDataInicial() {
        return DataInicial;
    }

    public void setDataInicial(Date DataInicial) {
        this.DataInicial = DataInicial;
    }

    public int getID_Equipe() {
        return ID_Equipe;
    }

    public void setID_Equipe(int ID_Equipe) {
        this.ID_Equipe = ID_Equipe;
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "ID_Projeto=" + ID_Projeto +
                ", Nome='" + Nome + '\'' +
                ", Descricao='" + Descricao + '\'' +
                ", DataEntrega=" + DataEntrega +
                ", DataInicial=" + DataInicial +
                ", ID_Equipe=" + ID_Equipe +
                '}';
    }
}
