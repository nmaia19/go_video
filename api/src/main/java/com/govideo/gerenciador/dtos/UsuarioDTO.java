package com.govideo.gerenciador.dtos;

import com.govideo.gerenciador.entities.Perfil;
import com.govideo.gerenciador.entities.Usuario;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO {

    private Long id;

    private String nome;

    private String email;

    private List<Perfil> perfis = new ArrayList<>();

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfis = usuario.getPerfis();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public static Page<UsuarioDTO> converterParaDTO(Page<Usuario> usuarios) {
        return usuarios.map(UsuarioDTO::new);
    }

}
