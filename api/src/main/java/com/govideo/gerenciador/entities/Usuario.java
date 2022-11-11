package com.govideo.gerenciador.entities;

import com.govideo.gerenciador.entities.enuns.StatusUsuario;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Column(columnDefinition = "DATETIME")
    private Instant criadoEm;

    @Column(columnDefinition = "DATETIME")
    private Instant atualizadoEm;

    @PrePersist
    public void prePersit(){
        criadoEm = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        atualizadoEm = Instant.now();
    }

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && status == usuario.status && Objects.equals(criadoEm, usuario.criadoEm) && Objects.equals(atualizadoEm, usuario.atualizadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, senha, status, criadoEm, atualizadoEm);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
