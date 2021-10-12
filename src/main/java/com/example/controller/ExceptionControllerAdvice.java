package com.example.controller;

import com.example.common.exception.BusinessException;
import com.example.common.exception.message.BusinessMessage;
import com.example.common.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * The class Exception controller advice.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * Handle runtime exception.
     *
     * @param e the Business exception
     * @return the response
     */
    @ExceptionHandler(BusinessException.class)
    public Response<?> handleRuntimeException(BusinessException e) {
        log.error("Runtime Exception: ", e.getStackTrace());
        return Response.build()
                       .code(Response.CODE_BUSINESS)
                       .data(e.getMessage());
    }

    /**
     * Handle exceptions from the rejected authentication request because the credentials are invalid.
     *
     * @param e the Username not found exception or Bad credentials exception
     * @return the response
     */
    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    public Response<?> handleSignInException(AuthenticationException e, HttpServletResponse response) {
        log.error("Authentication Exception: {}", e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return Response.build()
                       .code(Response.CODE_UNAUTHORIZED)
                       .data(BusinessMessage.INCORRECT_USERNAME_PASSWORD);
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
        log.error(ex.getMessage());
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Response.build().code(Response.CODE_VALIDATION).data(errors);
    }

    /**
     * Handle all exceptions
     *
     * @param e the Exception
     * @return the response
     */
    @ExceptionHandler(Exception.class)
    public Response<?> handleAllExceptions(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return Response.build()
                .code(Response.CODE_BUSINESS)
                .data(e.getMessage());
    }
}