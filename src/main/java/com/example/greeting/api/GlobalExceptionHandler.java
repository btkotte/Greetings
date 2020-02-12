package com.example.greeting.api;

import com.example.greeting.exception.InvalidRequestException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebInputException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ServerWebInputException.class})
    public Error invalidInputException(ServerWebInputException ex) {
        log.warn(ex.getMessage());
        String message = ex.getMessage();

        if (ex.getRootCause() instanceof NumberFormatException) {
            message = "Id should be between 1 and " + Long.MAX_VALUE;
        } else if (ex.getRootCause() instanceof IllegalArgumentException) {
            message = "Invalid value provided for " + ex.getMethodParameter().getParameterName();
        }

        return new Error(400, "Invalid Input", message);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({InvalidRequestException.class})
    public Error invalidInputException(InvalidRequestException ex) {
        log.warn(ex.getMessage());
        return new Error(400, "Invalid Input", ex.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Error unhandledException(Exception ex) {
        log.error(ex.getMessage());
        return new Error(500, "Unknown reason", ex.getMessage());
    }

    @Getter
    static class Error {
        private final int status;
        private final String reason;
        private final String message;

        Error(int status, String reason, String message) {
            this.status = status;
            this.reason = reason;
            this.message = message;
        }
    }
}