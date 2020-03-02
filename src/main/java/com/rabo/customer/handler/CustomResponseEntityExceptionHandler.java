package com.rabo.customer.handler;

import com.rabo.customer.dto.RaboResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleCustomException(ex);
    }

    public ResponseEntity<Object> handleCustomException (MethodArgumentNotValidException ex) {
        LOGGER.error("handleMethodArgumentNotValid");
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String error = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        LOGGER.error(error);
        RaboResponse response = new RaboResponse();
        response.setResult(ResultCodes.BAD_REQUEST);
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<RaboResponse> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        LOGGER.error("handleConstraintViolationException");
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        String error = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        LOGGER.error(error);
        RaboResponse response = new RaboResponse();
        response.setResult(ResultCodes.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionException.class)
    public final ResponseEntity<RaboResponse> handleTransactionException(TransactionException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getRaboResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<RaboResponse> handleUnCheckedException(Exception exception, WebRequest request) {
        RaboResponse response = new RaboResponse();
        response.setResult(ResultCodes.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
