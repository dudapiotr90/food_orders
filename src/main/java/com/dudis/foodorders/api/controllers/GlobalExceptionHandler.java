package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.domain.exception.MailException;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.domain.exception.SearchingException;
import com.dudis.foodorders.infrastructure.security.AuthorityException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.net.ConnectException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ConnectException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ModelAndView handleMailDevException(ConnectException ex) {
        String errorMessage = "Unexpected exception: Run maildev container and try again";
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler(value = MailException.class)
    public ModelAndView handleMailException(MailException ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler(value = FileUploadException.class)
    public ModelAndView handleFileUploadException(FileUploadException ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }


    @ExceptionHandler(value = {AuthorityException.class})
    public ModelAndView handleAuthorityException(AuthorityException ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }


//    @ExceptionHandler(value = {OrderException.class})
//    public ModelAndView handleAuthorityException(OrderException ex) {
//        String errorMessage = ex.getMessage();
//        log.error(errorMessage, ex);
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("errorMessage", errorMessage);
//        return modelAndView;
//    }

    @ExceptionHandler(value = RegistrationException.class)
    public ModelAndView handleRegistrationException(RegistrationException ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler(value = {SearchingException.class})
    public ModelAndView handleSearchingException(SearchingException ex) {
        String errorMessage = ex.getMessage();
        log.error(errorMessage, ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
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
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", errorMessage);
            return modelAndView;
        }
        String defaultMessage = "An error occurred.";
        log.error(defaultMessage, cex);

        ModelAndView defaultModelAndView = new ModelAndView("error");
        defaultModelAndView.addObject("errorMessage", defaultMessage);
        return defaultModelAndView;
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handlePhoneValidationException(MethodArgumentNotValidException mae) {
            String invalidFieldName = Objects.requireNonNull(mae.getBindingResult().getFieldError()).getField();
            String invalidValue = String.valueOf(mae.getBindingResult().getFieldError().getRejectedValue());

            String errorMessage = String.format(
                "Invalid input for field [%s]: [%s]. The correct format is: [%s] (%s)",
                invalidFieldName, invalidValue, "+xx xxx xxx xxx", "+00 000 000 000"
            );
            log.error(errorMessage, mae);
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", errorMessage);
            return modelAndView;
    }
}
