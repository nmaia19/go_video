package com.govideo.gerenciador.exceptions.handler;

public class ErroDeFormResponse {

    private String campo;
    private String erro;

    public ErroDeFormResponse(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }

}