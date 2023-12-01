package com.example.common.model;

import java.io.Serializable;
import java.util.Date;

public class Projeto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID_Projeto;
    private String Nome;
    private String Descricao;
    private Date DataEntrega;
    private Date DataInicial;
    private int ID_Equipe;
    private String nomeEquipe;
    private boolean Estado;

    public Projeto() {
        // Construtor vazio é necessário para serialização
    }

    public Projeto(int ID_Projeto, String Nome, String Descricao, Date DataEntrega, Date DataInicial, int ID_Equipe, boolean Estado) {
        this.ID_Projeto = ID_Projeto;
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.DataEntrega = DataEntrega;
        this.DataInicial = DataInicial;
        this.ID_Equipe = ID_Equipe;
        this.Estado = Estado;
    }

    public Projeto(String Nome, String Descricao, Date DataEntrega, Date DataInicial, int ID_Equipe, boolean Estado) {
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.DataEntrega = DataEntrega;
        this.DataInicial = DataInicial;
        this.ID_Equipe = ID_Equipe;
        this.Estado = Estado;
    }

    public Projeto(int ID_Projeto) {
        this.ID_Projeto = ID_Projeto;
    }

    // Getters e Setters
    public int getID_Projeto() {
        return ID_Projeto;
    }

    public void setID_Projeto(int ID_Projeto) {
        this.ID_Projeto = ID_Projeto;
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

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public boolean getEstado() {
        return Estado;
    }

    public void setEstado(boolean Estado) {
        this.Estado = Estado;
    }

    @Override
    public String toString() {
        return "Projeto{"
                + "ID_Projeto=" + ID_Projeto
                + ", Nome='" + Nome + '\''
                + ", Descricao='" + Descricao + '\''
                + ", DataEntrega=" + DataEntrega
                + ", DataInicial=" + DataInicial
                + ", ID_Equipe=" + ID_Equipe
                + ", Estado =" + Estado
                + '}';
    }

    public String getEstadoLiteral() {
        String retorno;
        if (Estado == true) {
            retorno = "Completo";
        } else {
            retorno = "Em andamento";
        }
        return retorno;
    }
}
