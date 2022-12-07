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

    public AlteraSenhaUsuarioForm() {

    }

    public AlteraSenhaUsuarioForm(String senhaAntiga, String novaSenha, String confirmaNovaSenha) {
        this.senhaAntiga = senhaAntiga;
        this.novaSenha = novaSenha;
        this.confirmaNovaSenha = confirmaNovaSenha;
    }

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
