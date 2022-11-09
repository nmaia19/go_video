package com.govideo.gerenciador.dtos;

import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import org.springframework.data.domain.Page;

public class EquipamentoDTO {

    private Long id;

    private String modelo;

    private String descricao;

    private String marca;

    private String categoria;

    private StatusEquipamento status;

    public EquipamentoDTO(Equipamento equipamento) {
        this.id = equipamento.getId();
        this.modelo = equipamento.getModelo();
        this.descricao = equipamento.getDescricao();
        this.marca = equipamento.getMarca();
        this.categoria = equipamento.getCategoria();
        this.status = equipamento.getStatus();
    }

    public Long getId() {
        return id;
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

    public StatusEquipamento getStatus() {
        return status;
    }

    public static Page<EquipamentoDTO> converterParaDTO(Page<Equipamento> equipamentos) {
        return equipamentos.map(EquipamentoDTO::new);
    }
}
