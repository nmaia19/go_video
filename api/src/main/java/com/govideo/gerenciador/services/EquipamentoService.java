package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.repositories.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public Page<EquipamentoDTO> consultar(Pageable paginacao) {
        Page<Equipamento> equipamentos = equipamentoRepository.findAll(paginacao);
        return EquipamentoDTO.converterParaDTO(equipamentos);
    }

    public EquipamentoDTO consultarPorId(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado!"));
        return new EquipamentoDTO(equipamento);
    }

    //TODO: consultarPorStatus(String status)

    @Transactional
    public EquipamentoDTO cadastrar(EquipamentoForm equipamentoForm) {
     Equipamento equipamento = equipamentoForm.converterParaEntidade();
     equipamento = equipamentoRepository.save(equipamento);
     return new EquipamentoDTO(equipamento);
    }

    //TODO: alterar

    //TODO: excluir

    //TODO: retirar e devolver

}
