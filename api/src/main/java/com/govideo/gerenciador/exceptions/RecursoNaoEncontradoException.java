package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;


public class RecursoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.NOT_FOUND;

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
