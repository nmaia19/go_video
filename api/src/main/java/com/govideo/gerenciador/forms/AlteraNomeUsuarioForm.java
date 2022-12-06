package com.govideo.gerenciador.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AlteraNomeUsuarioForm {
    @NotNull @NotEmpty
    private String nome;

    public String getNome() {
            return nome;
        }

}
