package com.govideo.gerenciador.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AlteraNomeUsuarioForm {

    @NotNull
    @NotEmpty
    private String nome;

    public AlteraNomeUsuarioForm() {
    }

    public AlteraNomeUsuarioForm(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
