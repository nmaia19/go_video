package com.govideo.gerenciador.exceptions.handler;

import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.exceptions.EquipamentoNaoDisponivelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> allExceptionHandler(Exception ex, HttpServletRequest req) {
        ExceptionResponse error = new ExceptionResponse(Instant.now(), ex.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public final ResponseEntity<ExceptionResponse> resourceNotFound(RecursoNaoEncontradoException r,
                                                                    HttpServletRequest req){
        ExceptionResponse error = new ExceptionResponse(Instant.now(), r.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error,r.getStatus());
    }

    @ExceptionHandler(EquipamentoNaoDisponivelException.class)
    public final ResponseEntity<ExceptionResponse> resourceNotFound(EquipamentoNaoDisponivelException e,
                                                                    HttpServletRequest req){
        ExceptionResponse error = new ExceptionResponse(Instant.now(), e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(error,e.getStatus());
    }


}
