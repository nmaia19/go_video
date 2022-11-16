package com.govideo.gerenciador.exceptions.handler;

import com.govideo.gerenciador.exceptions.RecursoNaoEncontradoException;
import com.govideo.gerenciador.exceptions.EquipamentoNaoDisponivelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormResponse> handle(MethodArgumentNotValidException exception) {
        List<ErroDeFormResponse> errosDTO = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormResponse erro = new ErroDeFormResponse(e.getField(), mensagem);
            errosDTO.add(erro);
        });
        return errosDTO;
    }

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
