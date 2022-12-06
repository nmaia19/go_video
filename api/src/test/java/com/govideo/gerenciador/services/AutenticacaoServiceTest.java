package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.EquipamentoDTO;
import com.govideo.gerenciador.dtos.RespostaDTO;
import com.govideo.gerenciador.entities.Emprestimo;
import com.govideo.gerenciador.entities.Equipamento;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusEquipamento;
import com.govideo.gerenciador.exceptions.OperacaoNaoPermitidaException;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.forms.EquipamentoForm;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    public Usuario mockUsuarioEntity() {
        return new Usuario("Nome", "usuario@email.com", "123");
    }

    @Test
    public void deveriaRetornarUsuarioAoBuscarPorEmail() {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(mockUsuarioEntity()));
        Usuario retornoUsuario = (Usuario) autenticacaoService.loadUserByUsername("usuario@email.com");
        assertNotNull(retornoUsuario);
    }

    @Test
    public void naoDeveriaRetornarUsuarioAoBuscarPorEmail() {
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            when(usuarioRepository.findByEmail(any())).thenReturn(Optional.empty());
          autenticacaoService.loadUserByUsername("usuario-inexistente@email.com");
        });
        assertEquals("Não existe cadastro com o email informado!", exception.getMessage());
    }

}
