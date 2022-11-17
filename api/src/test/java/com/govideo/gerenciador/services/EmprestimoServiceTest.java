package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EmprestimoDTO;
import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import com.govideo.gerenciador.exceptions.EquipamentoNaoDisponivelException;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.repositories.EmprestimoRepository;
import com.govideo.gerenciador.repositories.EquipamentoRepository;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EquipamentoService equipamentoService;

    public Equipamento mockEquipamentoEntity() {
        Equipamento equipamento = new Equipamento("Pocket Cinema 6K", "Filmadora profissional Pocket Cinema 6K", "Black Magic", "Filmadoras", "https://emania.vteximg.com.br/arquivos/ids/209607");
        equipamento.setId(1L);
        return equipamento;
    }

    public Usuario mockUsuarioEntity() {
        Usuario usuario = new Usuario(1L);
        return usuario;
    }

    public Emprestimo mockEmprestimoEntity() {
        Emprestimo emprestimo = new Emprestimo(mockEquipamentoEntity(), mockUsuarioEntity());
        emprestimo.setId(1L);
        return emprestimo;
    }

    public Page<Emprestimo> mockEmprestimoPage() {
        return new PageImpl<>(Collections.singletonList(mockEmprestimoEntity()));
    }

    @Test
    public void deveriaRetornarEmprestimoAoBuscarPorId() {
        when(emprestimoRepository.findById(any())).thenReturn(Optional.of(mockEmprestimoEntity()));
        Emprestimo retornoEmprestimo = emprestimoService.consultarPorId(1L);
        assertNotNull(retornoEmprestimo);
    }

    @Test
    public void deveriaRetornarEmprestimoDTOAoBuscarPorId() {
        when(emprestimoRepository.findById(any())).thenReturn(Optional.of(mockEmprestimoEntity()));
        EmprestimoDTO retornoEmprestimo = emprestimoService.consultarPorIdRetornarDTO(1L);
        assertNotNull(retornoEmprestimo);
    }

    @Test
    public void deveriaCadastrarEmprestimo() throws EquipamentoNaoDisponivelException {
        Emprestimo emprestimo = mockEmprestimoEntity();
        when(equipamentoService.consultarPorId(any())).thenReturn(emprestimo.getEquipamento());
        when(equipamentoRepository.findById(any())).thenReturn(Optional.of(emprestimo.getEquipamento()));
        when(equipamentoRepository.save(any())).thenReturn(emprestimo.getEquipamento());
        when(usuarioRepository.save(any())).thenReturn(emprestimo.getUsuario());
        when(emprestimoRepository.save(any())).thenReturn(emprestimo);
        EmprestimoDTO retornoEmprestimo = emprestimoService.cadastrar(1L);
        assertEquals(1L, (long) retornoEmprestimo.getId());
    }

    @Test
    public void naoDeveriaCadastrarEmprestimo() {
        Emprestimo emprestimo = mockEmprestimoEntity();
        emprestimo.getEquipamento().setStatus(StatusEquipamento.INDISPONIVEL);
        EquipamentoNaoDisponivelException exception = assertThrows(EquipamentoNaoDisponivelException.class, () -> {
            when(equipamentoService.consultarPorId(any())).thenReturn(emprestimo.getEquipamento());
            when(usuarioRepository.save(any())).thenReturn(emprestimo.getUsuario());

            emprestimoService.cadastrar(1L);
        });
        assertTrue(exception.getMessage().equals("O equipamento informado não está disponível para empréstimo."));
    }

    @Test
    public void naoDeveriaEncontrarEmprestimosDTOAoBuscarPorId() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            when(emprestimoRepository.findById(any())).thenReturn(Optional.empty());
            emprestimoService.consultarPorIdRetornarDTO(1L);
        });
        assertTrue(exception.getMessage().equals("Empréstimo não encontrado!"));
    }

    @Test
    public void naoDeveriaEncontrarEmprestimosAoBuscarPorId() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            when(emprestimoRepository.findById(any())).thenReturn(Optional.empty());
            emprestimoService.consultarPorId(1L);
        });
        assertTrue(exception.getMessage().equals("Empréstimo não encontrado!"));
    }


    @Test
    public void deveriaRetornarTodosEmprestimos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(emprestimoRepository.findAll(pageable)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTOPage = emprestimoService.consultar(pageable);
        assertEquals(1L, emprestimoDTOPage.getTotalElements());
    }

    @Test
    public void deveriaBuscarEncerrados() {
        Emprestimo emprestimo = mockEmprestimoEntity();
        emprestimo.setDataFim(LocalDateTime.now());
        Page<Emprestimo> emprestimoEncerrado = new PageImpl<>(Collections.singletonList(emprestimo));
        Pageable paginacao = PageRequest.of(0, 10);
        when(emprestimoRepository.findByDataFimIsNotNull(paginacao)).thenReturn(emprestimoEncerrado);
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEncerrados(paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaBuscarVigentes() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(emprestimoRepository.findByDataFimIsNull(paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarVigentes(paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaBuscarEmprestimosPorEquipamento() {
        Pageable paginacao = PageRequest.of(0, 10);
        Equipamento equipamento = mockEquipamentoEntity();
        when(equipamentoService.consultarPorId(any())).thenReturn(equipamento);
        when(emprestimoRepository.findByEquipamento(equipamento, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosPorEquipamento(1L, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaBuscarEmprestimosPorUsuario() {
        Pageable paginacao = PageRequest.of(0, 10);
        Usuario usuario = mockUsuarioEntity();
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.findByUsuario(usuario, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosPorUsuario(1L, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaBuscarEmprestimosVigentesPorUsuario() {
        Pageable paginacao = PageRequest.of(0, 10);
        Usuario usuario = mockUsuarioEntity();
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.findByUsuarioEStatus(1L, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosVigentesPorUsuario(1L, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaEncerrarEmprestimo() {
        Pageable paginacao = PageRequest.of(0, 10);
        Emprestimo emprestimo = mockEmprestimoEntity();
        when(emprestimoRepository.findById(any())).thenReturn(Optional.ofNullable(emprestimo));
        when(emprestimoRepository.save(any())).thenReturn(emprestimo);
        when(equipamentoService.alterarStatus(emprestimo.getEquipamento().getId(), StatusEquipamento.DISPONIVEL)).thenReturn(new EquipamentoDTO(emprestimo.getEquipamento()));
        EmprestimoDTO emprestimoDTO = emprestimoService.encerrar(1L);
        assertNotNull(emprestimoDTO);
        verify(emprestimoRepository, Mockito.times(1)).save(emprestimo);
    }
}
