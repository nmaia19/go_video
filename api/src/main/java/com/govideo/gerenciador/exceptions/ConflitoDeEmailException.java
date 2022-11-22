package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;

public class ConflitoDeEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.CONFLICT;
    public ConflitoDeEmailException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }
}
