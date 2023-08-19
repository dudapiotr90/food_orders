package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.domain.exception.*;
import com.dudis.foodorders.infrastructure.security.AuthorityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        String errorId = UUID.randomUUID().toString();
        String errorMessage = "Unexpected error occurred. ErrorId: [%s]".formatted(errorId);
        log.error(errorMessage, ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = {MailSendException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView handleMailDevException(ConnectException ex) {
        String errorMessage = "Unexpected exception: Run maildev container and try again";
        log.error(errorMessage, ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = MailException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView handleMailException(MailException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        BigDecimal fileSize = BigDecimal.valueOf(request.getContentLengthLong())
            .divide(BigDecimal.valueOf(1048576), 2, RoundingMode.HALF_UP);
        String errorMessage = "Maximum upload size exceeded. Max size 5MB. Trying to upload [%s] MB"
            .formatted(fileSize);
        log.error(errorMessage, ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = FileUploadException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView handleFileUploadException(FileUploadException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = {AuthorityException.class})
    public ModelAndView handleAuthorityException(AuthorityException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = {OrderException.class})
    public ModelAndView handleAuthorityException(OrderException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = RegistrationException.class)
    public ModelAndView handleRegistrationException(RegistrationException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NotFoundException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = {SearchingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleSearchingException(SearchingException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleValidationException(ValidationException ex) {
        String errorMessage = prepareExceptionInfo(ex);
        return prepareExceptionView(errorMessage);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleLoginValidationException(ConstraintViolationException cex) {
        Optional<ConstraintViolation<?>> constraintViolation = cex.getConstraintViolations().stream()
            .filter(violation -> "login".equals(violation.getPropertyPath().toString()))
            .findFirst();
        if (constraintViolation.isPresent()) {
            Object invalidValue = constraintViolation.get().getInvalidValue();
            String errorMessage = """
                Your login must have a minimum length of 4 characters and a maximum length of 12 characters.
                Input login value: [%s]
                """.formatted(invalidValue);
            log.error(errorMessage, cex);
            return prepareExceptionView(errorMessage);
        }
        String defaultMessage = "An error occurred. Check your input";
        log.error(defaultMessage, cex);
        return prepareExceptionView(defaultMessage);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handlePhoneValidationException(MethodArgumentNotValidException mae) {
        String invalidFieldName = Objects.requireNonNull(mae.getBindingResult().getFieldError()).getField();
        String invalidValue = String.valueOf(mae.getBindingResult().getFieldError().getRejectedValue());

        String errorMessage = String.format(
            "Invalid input for field [%s]: [%s]. Check your input",
            invalidFieldName, invalidValue);
        log.error(errorMessage, mae);
        return prepareExceptionView(errorMessage);
    }

    private String prepareExceptionInfo(Exception ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        return errorMessage;
    }

    private ModelAndView prepareExceptionView(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }


}
