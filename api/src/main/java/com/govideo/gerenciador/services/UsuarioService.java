package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.UsuarioDTO;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusUsuario;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isPresent()) {
            return usuario.get();
        }
        throw new UsernameNotFoundException("Email incorreto!");
    }

    public Page<UsuarioDTO> consultar(Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
        return UsuarioDTO.converterParaDTO(usuarios);
    }

    public UsuarioDTO consultarPorIdRetornarDTO(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));
        return new UsuarioDTO(usuario);
    }

    public Usuario consultarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));
    }

    public Page<UsuarioDTO> consultarPorStatus(String statusString, Pageable paginacao) {
        Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);

        if (statusString != null) {
            if (statusString.equalsIgnoreCase("ATIVO")) {
                usuarios = usuarioRepository.findByStatus(StatusUsuario.ATIVO, paginacao);
            } else if (statusString.equalsIgnoreCase("INATIVO")) {
                usuarios = usuarioRepository.findByStatus(StatusUsuario.INATIVO, paginacao);
            }
        }
        return UsuarioDTO.converterParaDTO(usuarios);
    }

}
