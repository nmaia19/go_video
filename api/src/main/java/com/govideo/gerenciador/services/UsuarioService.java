package com.govideo.gerenciador.services;

import com.govideo.gerenciador.dtos.UsuarioDTO;
import com.govideo.gerenciador.entities.Perfil;
import com.govideo.gerenciador.entities.Usuario;
import com.govideo.gerenciador.entities.enuns.StatusUsuario;
import com.govideo.gerenciador.exceptions.ConflitoDeEmailException;
import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.forms.UsuarioForm;
import com.govideo.gerenciador.repositories.PerfilRepository;
import com.govideo.gerenciador.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PerfilRepository perfilRepository;

  public final Perfil COLABORADOR = new Perfil("ROLE_COLABORADOR");

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

  @Transactional
  public UsuarioDTO cadastrar(UsuarioForm usuarioForm) {
    Usuario usuario = usuarioForm.converterParaEntidade();
    if(usuarioRepository.existsByEmail(usuario.getEmail())) {
      throw new ConflitoDeEmailException("Já existe cadastro com o e-mail informado!");
    }
    if(perfilRepository.existsByPerfil(COLABORADOR.getPerfil())) {
      usuario.getPerfis().add(COLABORADOR);
    }else{
      Perfil perfil = perfilRepository.save(COLABORADOR);
      usuario.getPerfis().add(perfil);
    }
    usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
    usuario = usuarioRepository.save(usuario);
    return new UsuarioDTO(usuario);
  }
}
