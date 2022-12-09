package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EmprestimoDTO;
import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Perfil;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EquipamentoService equipamentoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    public Equipamento mockEquipamentoEntity() {
        Equipamento equipamento = new Equipamento("Pocket Cinema 6K", "Filmadora profissional Pocket Cinema 6K", "Black Magic", "Filmadoras", "https://emania.vteximg.com.br/arquivos/ids/209607");
        equipamento.setId(1L);
        return equipamento;
    }

    public Usuario mockUsuarioEntity() {
        return new Usuario(1L);
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
    public void deveriaCadastrarEmprestimo() throws EquipamentoNaoDisponivelException {
        Emprestimo emprestimo = mockEmprestimoEntity();
        Usuario usuario = mockUsuarioEntity();
        when(equipamentoService.consultarPorId(any())).thenReturn(emprestimo.getEquipamento());
        when(equipamentoRepository.findById(any())).thenReturn(Optional.of(emprestimo.getEquipamento()));
        when(equipamentoRepository.save(any())).thenReturn(emprestimo.getEquipamento());
        when(emprestimoRepository.save(any())).thenReturn(emprestimo);
        EmprestimoDTO retornoEmprestimo = emprestimoService.cadastrar(1L, usuario);
        assertEquals(1L, (long) retornoEmprestimo.getId());
    }

    @Test
    public void naoDeveriaCadastrarEmprestimo() {
        Emprestimo emprestimo = mockEmprestimoEntity();
        Usuario usuario = mockUsuarioEntity();
        emprestimo.getEquipamento().setStatus(StatusEquipamento.INDISPONÍVEL);
        EquipamentoNaoDisponivelException exception = assertThrows(EquipamentoNaoDisponivelException.class, () -> {
            when(equipamentoService.consultarPorId(any())).thenReturn(emprestimo.getEquipamento());
            when(usuarioRepository.save(any())).thenReturn(emprestimo.getUsuario());

            emprestimoService.cadastrar(1L, usuario);
        });
        assertEquals("O equipamento informado não está disponível para empréstimo.", exception.getMessage());
    }

    @Test
    public void deveriaRetornarEmprestimoAoBuscarPorId() {
        when(emprestimoRepository.findById(any())).thenReturn(Optional.of(mockEmprestimoEntity()));
        Emprestimo retornoEmprestimo = emprestimoService.consultarPorId(1L);
        assertNotNull(retornoEmprestimo);
    }

    @Test
    public void naoDeveriaRetornarEmprestimoAoBuscarPorId() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            when(emprestimoRepository.findById(any())).thenReturn(Optional.empty());
            emprestimoService.consultarPorId(1L);
        });
        assertEquals("Empréstimo não encontrado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarEmprestimoDTOAoBuscarPorId() {
        Usuario usuario = mockUsuarioEntity();
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(new Perfil("ROLE_COLABORADOR"));
        usuario.setPerfis(perfis);
        when(emprestimoRepository.findById(any())).thenReturn(Optional.of(mockEmprestimoEntity()));
        EmprestimoDTO retornoEmprestimo = emprestimoService.consultarPorIdRetornarDTO(1L, usuario);
        assertNotNull(retornoEmprestimo);
    }

    @Test
    public void naoDeveriaRetornarEmprestimoDTOAoBuscarPorId() {
        Usuario usuario = mockUsuarioEntity();
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> {
            when(emprestimoRepository.findById(any())).thenReturn(Optional.empty());
            emprestimoService.consultarPorIdRetornarDTO(1L, usuario);
        });
        assertEquals("Empréstimo não encontrado!", exception.getMessage());
    }

    @Test
    public void deveriaRetornarTodosOsEmprestimos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(emprestimoRepository.findAll(pageable)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTOPage = emprestimoService.consultar(pageable);
        assertEquals(1L, emprestimoDTOPage.getTotalElements());
    }

    @Test
    public void deveriaRetornarEmprestimosEncerrados() {
        Emprestimo emprestimo = mockEmprestimoEntity();
        emprestimo.setDataFim(LocalDateTime.now());
        Page<Emprestimo> emprestimoEncerrado = new PageImpl<>(Collections.singletonList(emprestimo));
        Pageable paginacao = PageRequest.of(0, 10);
        when(emprestimoRepository.findByDataFimIsNotNull(paginacao)).thenReturn(emprestimoEncerrado);
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEncerrados(paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEmprestimosVigentes() {
        Pageable paginacao = PageRequest.of(0, 10);
        when(emprestimoRepository.findByDataFimIsNull(paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarVigentes(paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEmprestimosPorEquipamento() {
        Pageable paginacao = PageRequest.of(0, 10);
        Equipamento equipamento = mockEquipamentoEntity();
        when(equipamentoService.consultarPorId(any())).thenReturn(equipamento);
        when(emprestimoRepository.findByEquipamento(equipamento, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosPorEquipamento(1L, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEmprestimosPorUsuario() {
        Pageable paginacao = PageRequest.of(0, 10);
        Usuario usuario = mockUsuarioEntity();
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(new Perfil("ROLE_COLABORADOR"));
        usuario.setPerfis(perfis);
        when(usuarioService.consultarPorId(any())).thenReturn(usuario);
        when(emprestimoRepository.findByUsuario(usuario, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosPorUsuario(1L, usuario, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEmprestimosEncerradosPorUsuario() {
        Pageable paginacao = PageRequest.of(0, 10);
        Usuario usuario = mockUsuarioEntity();
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(new Perfil("ROLE_COLABORADOR"));
        usuario.setPerfis(perfis);
        when(usuarioService.consultarPorId(any())).thenReturn(usuario);
        when(emprestimoRepository.findEncerradosByUsuario(1L, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosEncerradosPorUsuario(1L, usuario, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaRetornarEmprestimosVigentesPorUsuario() {
        Pageable paginacao = PageRequest.of(0, 10);
        Usuario usuario = mockUsuarioEntity();
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(new Perfil("ROLE_COLABORADOR"));
        usuario.setPerfis(perfis);
        when(usuarioService.consultarPorId(any())).thenReturn(usuario);
        when(emprestimoRepository.findVigentesByUsuario(1L, paginacao)).thenReturn(mockEmprestimoPage());
        Page<EmprestimoDTO> emprestimoDTO = emprestimoService.consultarEmprestimosVigentesPorUsuario(1L, usuario, paginacao);
        assertEquals(1L, emprestimoDTO.getTotalElements());
    }

    @Test
    public void deveriaEncerrarEmprestimo() {
        Pageable paginacao = PageRequest.of(0, 10);
        Emprestimo emprestimo = mockEmprestimoEntity();
        Usuario usuario = mockUsuarioEntity();
        List<Perfil> perfis = new ArrayList<>();
        perfis.add(new Perfil("ROLE_ADMINISTRADOR"));
        usuario.setPerfis(perfis);
        when(emprestimoRepository.findById(any())).thenReturn(Optional.ofNullable(emprestimo));
        when(emprestimoRepository.save(any())).thenReturn(emprestimo);
        when(equipamentoService.alterarStatus(emprestimo.getEquipamento().getId(), StatusEquipamento.DISPONÍVEL)).thenReturn(new EquipamentoDTO(emprestimo.getEquipamento()));
        EmprestimoDTO emprestimoDTO = emprestimoService.encerrar(1L, usuario);
        assertNotNull(emprestimoDTO);
        verify(emprestimoRepository, Mockito.times(1)).save(emprestimo);
    }

}
