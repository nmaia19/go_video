package com.govideo.gerenciador.forms;

import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EquipamentoForm {

    @NotNull @NotEmpty
    private String modelo;

    @NotNull @NotEmpty
    private String descricao;

    @NotNull @NotEmpty
    private String marca;

    @NotNull @NotEmpty
    private String categoria;

    @NotNull @NotEmpty
    private String urlFoto;

    public EquipamentoForm() {

    }

    public EquipamentoForm(String modelo, String descricao, String marca, String categoria, String urlFoto) {
        this.modelo = modelo;
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
        this.urlFoto = urlFoto;
    }

    public String getModelo() {
        return modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Equipamento converterParaEntidade() {
        return new Equipamento(modelo, descricao, marca, categoria, urlFoto);
    }

}
