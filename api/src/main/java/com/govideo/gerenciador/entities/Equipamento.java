package com.govideo.gerenciador.entities;

import com.govideo.gerenciador.entities.enuns.StatusEquipamento;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Equipamento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    private String descricao;

    private String marca;

    private String categoria;

    private String urlFoto;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status = StatusEquipamento.DISPONIVEL;

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

    public Equipamento() {

    }

    public Equipamento(String modelo, String descricao, String marca, String categoria, String urlFoto) {
        this.modelo = modelo;
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
        this.urlFoto = urlFoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipamento that = (Equipamento) o;
        return Objects.equals(id, that.id) && Objects.equals(modelo, that.modelo) && Objects.equals(descricao, that.descricao) && Objects.equals(marca, that.marca) && Objects.equals(categoria, that.categoria) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modelo, descricao, marca, categoria, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public StatusEquipamento getStatus() {
        return status;
    }

    public void setStatus(StatusEquipamento status) {
        this.status = status;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }
}
