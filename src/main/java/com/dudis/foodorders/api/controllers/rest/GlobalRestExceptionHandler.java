package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.domain.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.mail.iap.ConnectionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(annotations = RestController.class)
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
        ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
        IllegalArgumentException.class, HttpStatus.BAD_REQUEST,
        DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
        MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST,
        EntityNotFoundException.class, HttpStatus.NOT_FOUND,
        NotFoundException.class, HttpStatus.NOT_FOUND,
        ConnectException.class, HttpStatus.SERVICE_UNAVAILABLE,
        ConnectionException.class, HttpStatus.SERVICE_UNAVAILABLE,
        JsonProcessingException.class, HttpStatus.INTERNAL_SERVER_ERROR
    );

    @Override
    protected Mono<ResponseEntity<Object>> handleExceptionInternal(
        Exception ex,
        Object body,
        HttpHeaders headers,
        HttpStatusCode status,
        ServerWebExchange exchange
    ) {
        return super.handleExceptionInternal(ex, body, headers, status, exchange);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception exception) {
        return doHandle(exception, getStatusFromMap(exception.getClass()));
    }

    private ResponseEntity<?> doHandle(Exception exception, HttpStatus status) {
        final String errorId = UUID.randomUUID().toString();
        log.error("Exception: ID={}, HttpStatus={}",errorId,status,exception );

        return ResponseEntity
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorId);
    }


    private HttpStatus getStatusFromMap(final Class<?> exceptionClass) {
        return EXCEPTION_STATUS.getOrDefault(exceptionClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
