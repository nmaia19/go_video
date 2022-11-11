package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;

public class EquipamentoNaoDisponivelException extends Throwable {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.BAD_REQUEST;
    public EquipamentoNaoDisponivelException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
