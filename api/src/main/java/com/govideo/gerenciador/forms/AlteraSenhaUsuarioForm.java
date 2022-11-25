package com.govideo.gerenciador.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AlteraSenhaUsuarioForm {

    @NotNull @NotEmpty
    private String senhaAntiga;

    @NotNull @NotEmpty
    private String novaSenha;

    @NotNull @NotEmpty
    private String confirmaNovaSenha;

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public String getConfirmaNovaSenha() {
        return confirmaNovaSenha;
    }

}
