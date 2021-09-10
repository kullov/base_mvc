package org.ttnga.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ttnga.common.exception.BusinessException;
import org.ttnga.common.http.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Exception controller advice.
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * The Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    /**
     * Handle runtime exception response.
     *
     * @param exception the exception
     * @return the response
     */
    @ExceptionHandler(BusinessException.class)
    public Response<?> handleRuntimeException(BusinessException exception) {
        logger.error("RuntimeException", exception);
        return Response.build()
                .code(Response.CODE_BUSINESS)
                .data(exception.getMessage());
    }

    /**
     * Handle validation exceptions response.
     *
     * @param ex the ex
     * @return the response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Response.build().code(Response.CODE_VALIDATION).data(errors);
    }
}
