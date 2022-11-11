package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import com.govideo.gerenciador.forms.EquipamentoForm;
import com.govideo.gerenciador.repositories.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public Page<EquipamentoDTO> consultar(Pageable paginacao) {
        Page<Equipamento> equipamentos = equipamentoRepository.findAll(paginacao);
        return EquipamentoDTO.converterParaDTO(equipamentos);
    }

    public EquipamentoDTO consultarPorIdRetornarDTO(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado!"));
        return new EquipamentoDTO(equipamento);
    }

    public Equipamento consultarPorId(Long id) {
        return equipamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado!"));
    }

    public Page<EquipamentoDTO> consultarPorStatus(String statusString, Pageable paginacao) {
        Page<Equipamento> equipamentos = equipamentoRepository.findAll(paginacao);

        if(statusString != null) {
            if (statusString.equalsIgnoreCase("DISPONIVEL")) {
                equipamentos = equipamentoRepository.findByStatus(StatusEquipamento.DISPONIVEL, paginacao);
            } else if (statusString.equalsIgnoreCase("INDISPONIVEL")) {
                equipamentos = equipamentoRepository.findByStatus(StatusEquipamento.INDISPONIVEL, paginacao);
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
        Equipamento equipamento = equipamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado!"));

        equipamento.setModelo(equipamentoForm.getModelo());
        equipamento.setDescricao(equipamentoForm.getDescricao());
        equipamento.setMarca(equipamentoForm.getMarca());
        equipamento.setCategoria(equipamentoForm.getCategoria());
        equipamento.setUrlFoto(equipamentoForm.getUrlFoto());
        equipamento = equipamentoRepository.save(equipamento);

        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public EquipamentoDTO alterarStatus(Long id, String statusString) {
        Equipamento equipamento = consultarPorId(id);

        if(statusString != null) {
            if (statusString.equalsIgnoreCase("DISPONIVEL")) {
                equipamento.setStatus(StatusEquipamento.DISPONIVEL);
            } else if (statusString.equalsIgnoreCase("INDISPONIVEL")) {
                equipamento.setStatus(StatusEquipamento.INDISPONIVEL);
            } else if (statusString.equalsIgnoreCase("INATIVO")) {
                equipamento.setStatus(StatusEquipamento.INATIVO);
            }
            equipamento = equipamentoRepository.save(equipamento);
        }
        return new EquipamentoDTO(equipamento);
    }

    @Transactional
    public String excluir(Long id) {
        Equipamento equipamento = consultarPorId(id);
        String mensagem;

        //TODO: public List<Emprestimo> consultarEmprestimosPorEquipamento(Long IdEquipamento){} na Service de Emprestimo;
//         if(emprestimoService.consultarEmprestimosPorEquipamento(id).size() > 0) {
//             if(equipamento.getStatus().equals(StatusEquipamento.DISPONIVEL)) {
//                alterarStatus(id, StatusEquipamento.INATIVO);
//                mensagem = "Equipamento de ID " + id + " inativado com sucesso!";
//             } else {
//                 mensagem = "O status atual do equipamento de ID " + id + " é " + equipamento.getStatus();
//             }
//         } else {
            equipamentoRepository.delete(equipamento);
            mensagem = "Equipamento de ID " + id + " excluído com sucesso!";
//         }
        return mensagem;
    }

}
