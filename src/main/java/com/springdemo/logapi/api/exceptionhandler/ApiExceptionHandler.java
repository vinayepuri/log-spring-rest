package com.springdemo.logapi.api.exceptionhandler;

import com.springdemo.logapi.domain.exception.EntidadeNaoEncontradaException;
import com.springdemo.logapi.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Problema.Campo> campos = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            campos.add(new Problema.Campo(nome, mensagem));
        });

        Problema problema = new Problema(status.value(), OffsetDateTime.now(), "Um ou mais campos estão inválidos", campos);

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
        HttpStatus httpStatus = ex instanceof EntidadeNaoEncontradaException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        Problema problema = new Problema(httpStatus.value(), OffsetDateTime.now(), ex.getMessage(), null);

        return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
    }

//    @ExceptionHandler(EntidadeNaoEncontradaException.class)
//    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request){
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        Problema problema = new Problema(httpStatus.value(), OffsetDateTime.now(), ex.getMessage(), null);
//
//        return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
//    }
}
