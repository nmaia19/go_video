package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;

public class OperacaoNaoPermitidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.FORBIDDEN;

    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
