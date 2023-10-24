package com.example.common.model;

import java.io.Serializable;

public class Anexo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID_Anexo;
    private String Nome;
    private byte[] Dados; // Representação binária do anexo
    private int ID_Tarefa;

    public Anexo() {
        // Construtor vazio é necessário para serialização
    }

    public Anexo(int ID_Anexo, String Nome, byte[] Dados, int ID_Tarefa) {
        this.ID_Anexo = ID_Anexo;
        this.Nome = Nome;
        this.Dados = Dados;
        this.ID_Tarefa = ID_Tarefa;
    }

    // Getters e Setters
    public int getID_Anexo() {
        return this.ID_Anexo;
    }

    public void setID_Anexo(int ID_Anexo) {
        this.ID_Anexo = ID_Anexo;
    }

    public String getNome() {
        return this.Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public byte[] getDados() {
        return this.Dados;
    }

    public void setDados(byte[] Dados) {
        this.Dados = Dados;
    }

    public int getID_Tarefa() {
        return this.ID_Tarefa;
    }

    public void setID_Tarefa(int ID_Tarefa) {
        this.ID_Tarefa = ID_Tarefa;
    }

    @Override
    public String toString() {
        return "{" +
                " ID_Anexo='" + getID_Anexo() + "'" +
                ", Nome='" + getNome() + "'" +
                ", Dados='" + getDados() + "'" +
                ", ID_Tarefa='" + getID_Tarefa() + "'" +
                "}";
    }
}
