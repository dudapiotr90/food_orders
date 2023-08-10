package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.domain.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS = Map.of(
        ConstraintViolationException.class, HttpStatus.BAD_REQUEST,
        IllegalArgumentException.class, HttpStatus.BAD_REQUEST,
        DataIntegrityViolationException.class, HttpStatus.BAD_REQUEST,
        EntityNotFoundException.class, HttpStatus.NOT_FOUND,
        NotFoundException.class, HttpStatus.NOT_FOUND,
        ConnectException.class, HttpStatus.SERVICE_UNAVAILABLE,
        JsonProcessingException.class, HttpStatus.INTERNAL_SERVER_ERROR
    );

}
