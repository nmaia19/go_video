package com.govideo.gerenciador.exceptions;

import org.springframework.http.HttpStatus;

public class CredenciaisIncorretasException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    HttpStatus status = HttpStatus.UNAUTHORIZED;

    public CredenciaisIncorretasException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }

}
