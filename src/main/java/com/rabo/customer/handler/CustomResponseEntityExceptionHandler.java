package com.rabo.customer.handler;

import com.rabo.customer.dto.CustomResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

/**
 * This CustomResponseEntityExceptionHandler handles exceptions and return ResponseEntity with
 * CustomResponseEntity and HttpStatus code.
 *
 * @author Nagaraju Kommineni
 */
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);

    /**
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(InvalidTransactionException.class)
    public final ResponseEntity<CustomResponseEntity> handleInvalidTransactionException(InvalidTransactionException exception) {
        LOGGER.error("handleInvalidTransactionException");
        return new ResponseEntity<>(exception.getCustomResponseEntity(), HttpStatus.OK);
    }

    /**
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<CustomResponseEntity> handleConstraintViolationException(ConstraintViolationException exception) {
        LOGGER.error("handleConstraintViolationException");
        CustomResponseEntity response = new CustomResponseEntity(ResultCodes.BAD_REQUEST, Collections.EMPTY_LIST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomResponseEntity> handleGenaricException(Exception exception) {
        LOGGER.error("handleCheckedAndUnCheckedException");
        CustomResponseEntity response = new CustomResponseEntity();
        response.setResult(ResultCodes.INTERNAL_SERVER_ERROR);
        response.setErrorRecords(Collections.emptyList());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
