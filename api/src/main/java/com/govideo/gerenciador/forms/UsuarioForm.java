package com.govideo.gerenciador.forms;

import com.govideo.gerenciador.entities.Usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UsuarioForm {

    @NotNull @NotEmpty
    private String nome;

    @NotNull @NotEmpty @Email
    private String email;

    @NotNull @NotEmpty
    private String senha;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario converterParaEntidade() {
        return new Usuario(nome, email, senha);
    }

}
