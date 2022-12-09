package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EmprestimoDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Perfil;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import com.govideo.gerenciador.exceptions.EquipamentoNaoDisponivelException;
import com.govideo.gerenciador.exceptions.OperacaoNaoPermitidaException;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.repositories.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private EquipamentoService equipamentoService;

    public Page<EmprestimoDTO> consultar(Pageable paginacao) {
        Page<Emprestimo> emprestimos = emprestimoRepository.findAll(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public EmprestimoDTO consultarPorIdRetornarDTO(Long id, Usuario usuarioLogado) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado!"));

        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();
        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !usuarioLogado.equals(emprestimo.getUsuario())) {
            throw new OperacaoNaoPermitidaException("Não é possível consultar empréstimos de outro colaborador!");
        }
        return new EmprestimoDTO(emprestimo);
    }

    public Emprestimo consultarPorId(Long id) {
        return emprestimoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo não encontrado!"));
    }

    public Page<EmprestimoDTO> consultarEncerrados(Pageable paginacao) {
        Page<Emprestimo> emprestimos = emprestimoRepository.findByDataFimIsNotNull(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarVigentes(Pageable paginacao) {
        Page<Emprestimo> emprestimos = emprestimoRepository.findByDataFimIsNull(paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosPorEquipamento(Long idEquipamento, Pageable paginacao) {
        Equipamento equipamento = equipamentoService.consultarPorId(idEquipamento);
        Page<Emprestimo> emprestimos = emprestimoRepository.findByEquipamento(equipamento, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosPorUsuario(Long idUsuario, Usuario usuarioLogado, Pageable paginacao) {
        Usuario usuario = usuarioService.consultarPorId(idUsuario);
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(idUsuario)) {
            throw new OperacaoNaoPermitidaException("Não é possível consultar empréstimos de outro colaborador!");
        }

        Page<Emprestimo> emprestimos = emprestimoRepository.findByUsuario(usuario, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosEncerradosPorUsuario(Long idUsuario, Usuario usuarioLogado, Pageable paginacao) {
        Usuario usuario = usuarioService.consultarPorId(idUsuario);
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(idUsuario)) {
            throw new OperacaoNaoPermitidaException("Não é possível consultar empréstimos de outro colaborador!");
        }

        Page<Emprestimo> emprestimos = emprestimoRepository.findEncerradosByUsuario(idUsuario, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    public Page<EmprestimoDTO> consultarEmprestimosVigentesPorUsuario(Long idUsuario, Usuario usuarioLogado, Pageable paginacao) {
        Usuario usuario = usuarioService.consultarPorId(idUsuario);
        Long idUsuarioLogado = usuarioLogado.getId();
        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !idUsuarioLogado.equals(idUsuario)) {
            throw new OperacaoNaoPermitidaException("Não é possível consultar empréstimos de outro colaborador!");
        }

        Page<Emprestimo> emprestimos = emprestimoRepository.findVigentesByUsuario(idUsuario, paginacao);
        return EmprestimoDTO.converterParaDTO(emprestimos);
    }

    @Transactional
    public EmprestimoDTO cadastrar(Long idEquipamento, Usuario usuarioLogado) throws EquipamentoNaoDisponivelException {
        Equipamento equipamento = equipamentoService.consultarPorId(idEquipamento);

        if (equipamento.getStatus() == StatusEquipamento.DISPONÍVEL) {
            Emprestimo emprestimo = new Emprestimo(equipamento, usuarioLogado);
            emprestimo = emprestimoRepository.save(emprestimo);
            equipamentoService.alterarStatus(idEquipamento, StatusEquipamento.INDISPONÍVEL);
            return new EmprestimoDTO(emprestimo);
        } else {
            throw new EquipamentoNaoDisponivelException("O equipamento informado não está disponível para empréstimo.");
        }
    }

    @Transactional
    public EmprestimoDTO encerrar(Long id, Usuario usuarioLogado) {
        Emprestimo emprestimo = consultarPorId(id);

        List<Perfil> perfilUsuarioLogado = usuarioLogado.getPerfis();

        if (!perfilUsuarioLogado.get(0).getPerfil().equals("ROLE_ADMINISTRADOR") && !usuarioLogado.equals(emprestimo.getUsuario())) {
            throw new OperacaoNaoPermitidaException("Não é possível encerrar empréstimos de outro colaborador!");
        }

        emprestimo.setDataFim(LocalDateTime.now());
        emprestimoRepository.save(emprestimo);
        equipamentoService.alterarStatus(emprestimo.getEquipamento().getId(), StatusEquipamento.DISPONÍVEL);
        return new EmprestimoDTO(emprestimo);
    }

}
