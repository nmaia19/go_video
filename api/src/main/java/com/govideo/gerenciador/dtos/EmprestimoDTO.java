package com.govideo.gerenciador.dtos;

import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Usuario;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class EmprestimoDTO {

    private Long id;

    private Equipamento equipamento;

    private Usuario usuario;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    public EmprestimoDTO(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.equipamento = emprestimo.getEquipamento();
        this.usuario = emprestimo.getUsuario();
        this.dataInicio = emprestimo.getDataInicio();
        this.dataFim = emprestimo.getDataFim();
    }

    public Long getId() {
        return id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public static Page<EmprestimoDTO> converterParaDTO(Page<Emprestimo> emprestimos) {
        return emprestimos.map(EmprestimoDTO::new);
    }
}
