package com.example.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID_Pessoa;
    private String cpf;
    private String Usuario;
    private String Email;
    private String Senha;
    private int Tipo; // 0 para gerente, 1 para funcionário
    private List<Equipe> equipes;

    public Pessoa() {
        // Construtor vazio é necessário para serialização
    }

    public Pessoa(int ID_Pessoa, String cpf, String Usuario, String Email, String Senha, int Tipo, List<Equipe> equipes) {
        this.ID_Pessoa = ID_Pessoa;
        this.cpf = cpf;
        this.Usuario = Usuario;
        this.Email = Email;
        this.Senha = Senha;
        this.Tipo = Tipo;
        this.equipes = equipes;
    }

    public Pessoa(int ID_Pessoa, String cpf, String Usuario, String Email, String Senha, int Tipo) {
        this.ID_Pessoa = ID_Pessoa;
        this.cpf = cpf;
        this.Usuario = Usuario;
        this.Email = Email;
        this.Senha = Senha;
        this.Tipo = Tipo;
        this.equipes = new ArrayList<>();
    }

    public Pessoa(String cpf, String Usuario, String Email, String Senha) {
        this.cpf = cpf;
        this.Usuario = Usuario;
        this.Email = Email;
        this.Senha = Senha;
    }

    public Pessoa(String Usuario, String Senha) {
        this.Usuario = Usuario;
        this.Senha = Senha;
    }

    public Pessoa(int ID_Pessoa, String cpf, String email) {
        this.ID_Pessoa = ID_Pessoa;
        this.cpf = cpf;
        this.Email = email;
    }

    public Pessoa(int ID_Pessoa) {
        this.ID_Pessoa = ID_Pessoa;
    }

    // Getters e Setters
    public int getID_Pessoa() {
        return ID_Pessoa;
    }

    public void setID_Pessoa(int ID_Pessoa) {
        this.ID_Pessoa = ID_Pessoa;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "ID_Pessoa=" + ID_Pessoa +
                ", cpf='" + cpf + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Email='" + Email + '\'' +
                ", Senha='" + Senha + '\'' +
                ", equipes=" + equipes +
                '}';
    }

    public String getFormatarCPF() {
        String retorno;
        String cpfFormatado= String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9)
        );
        retorno = cpfFormatado;
        return retorno;
    }
}
