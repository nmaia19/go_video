package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.dtos.RespostaDTO;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.repositories.EmprestimoRepository;
import com.govideo.gerenciador.repositories.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Page<EquipamentoDTO> consultar(Pageable paginacao) {
        Page<Equipamento> equipamentos = equipamentoRepository.findAll(paginacao);
        return EquipamentoDTO.converterParaDTO(equipamentos);
    }

    public EquipamentoDTO consultarPorIdRetornarDTO(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado!"));
        return new EquipamentoDTO(equipamento);
    }

    public Equipamento consultarPorId(Long id) {
        return equipamentoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado!"));
    }

    public Page<EquipamentoDTO> consultarPorStatus(String statusString, Pageable paginacao) {
        Page<Equipamento> equipamentos = equipamentoRepository.findAll(paginacao);

        if (statusString != null) {
            if (statusString.equalsIgnoreCase("DISPONIVEL")) {
                equipamentos = equipamentoRepository.findByStatus(StatusEquipamento.DISPONÍVEL, paginacao);
            } else if (statusString.equalsIgnoreCase("INDISPONIVEL")) {
                equipamentos = equipamentoRepository.findByStatus(StatusEquipamento.INDISPONÍVEL, paginacao);
            } else if (statusString.equalsIgnoreCase("INATIVO")) {
                equipamentos = equipamentoRepository.findByStatus(StatusEquipamento.INATIVO, paginacao);
            }
        }
        return EquipamentoDTO.converterParaDTO(equipamentos);
    }

    @Transactional
    public EquipamentoDTO cadastrar(EquipamentoForm equipamentoForm) {
        Equipamento equipamento = equipamentoForm.converterParaEntidade();
        equipamento = equipamentoRepository.save(equipamento);
        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public EquipamentoDTO alterar(Long id, EquipamentoForm equipamentoForm) {
        Equipamento equipamento = consultarPorId(id);

        equipamento.setModelo(equipamentoForm.getModelo());
        equipamento.setDescricao(equipamentoForm.getDescricao());
        equipamento.setMarca(equipamentoForm.getMarca());
        equipamento.setCategoria(equipamentoForm.getCategoria());
        equipamento.setUrlFoto(equipamentoForm.getUrlFoto());
        equipamento = equipamentoRepository.save(equipamento);

        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public EquipamentoDTO alterarStatus(Long id, StatusEquipamento status) {
        Equipamento equipamento = consultarPorId(id);
        equipamento.setStatus(status);
        equipamento = equipamentoRepository.save(equipamento);

        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public RespostaDTO excluir(Long id) {
        Equipamento equipamento = consultarPorId(id);
        String mensagem;
        Pageable paginacao = PageRequest.of(0, 10);
        
        if(emprestimoRepository.findByEquipamento(equipamento, paginacao).hasContent()) {
             if(equipamento.getStatus().equals(StatusEquipamento.DISPONÍVEL)) {
                alterarStatus(id, StatusEquipamento.INATIVO);
                mensagem = "Equipamento de ID " + id + " inativado com sucesso!";
             } else {
                 mensagem = "O status atual do equipamento de ID " + id + " é " + equipamento.getStatus() + ", então ele não pode ser inativado ou excluído!";
             }
         } else {
        equipamentoRepository.delete(equipamento);
        mensagem = "Equipamento de ID " + id + " excluído com sucesso!";
         }
        return new RespostaDTO(mensagem);
    }

}
