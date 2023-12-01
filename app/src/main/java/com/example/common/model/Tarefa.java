package com.example.common.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID_Tarefa;
    private String Nome;
    private String Descricao;
    private String HorasTrabalhadas;
    private int ID_Projeto;
    private int ID_Pessoa;
    private Date dataEntrega;
    private boolean Estado;

    public Tarefa() {
        // Construtor vazio é necessário para serialização
    }

    public Tarefa(int ID_Tarefa, String Nome, String Descricao, String HorasTrabalhadas, int ID_Projeto, int ID_Pessoa, Date dataEntrega, boolean Estado) {
        this.ID_Tarefa = ID_Tarefa;
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.HorasTrabalhadas = HorasTrabalhadas;
        this.ID_Projeto = ID_Projeto;
        this.ID_Pessoa = ID_Pessoa;
        this.dataEntrega = dataEntrega;
        this.Estado = Estado;
    }

    public Tarefa(int ID_Tarefa, String Nome, String Descricao, String HorasTrabalhadas, int ID_Projeto, Date dataEntrega, boolean Estado) {
        this.ID_Tarefa = ID_Tarefa;
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.HorasTrabalhadas = HorasTrabalhadas;
        this.ID_Projeto = ID_Projeto;
        this.dataEntrega = dataEntrega;
        this.Estado = Estado;
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

    public String getHorasTrabalhadas() {
        return HorasTrabalhadas;
    }

    public void setHorasTrabalhadas(String HorasTrabalhadas) {
        this.HorasTrabalhadas = HorasTrabalhadas;
    }

    public int getID_Projeto() {
        return ID_Projeto;
    }

    public void setID_Projeto(int ID_Projeto) {
        this.ID_Projeto = ID_Projeto;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public boolean getEstado() {
        return Estado;
    }

    public void setEstado(boolean Estado) {
        this.Estado = Estado;
    }

    public int getID_Pessoa() {
        return ID_Pessoa;
    }

    public void setID_Pessoa(int ID_Pessoa) {
        this.ID_Pessoa = ID_Pessoa;
    }

    @Override
    public String toString() {
        return "Tarefa{"
                + "ID_Tarefa=" + ID_Tarefa
                + ", Nome='" + Nome + '\''
                + ", Descricao='" + Descricao + '\''
                + ", HorasTrabalhadas=" + HorasTrabalhadas
                + ", ID_Projeto=" + ID_Projeto
                + ", Data Entrega=" + dataEntrega
                + ", Estado=" + Estado
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
