package com.bartzilla.controllers;

import com.bartzilla.exceptions.NotAuthorizedException;
import com.bartzilla.exceptions.NotFoundException;
import com.bartzilla.exceptions.NotSuitableTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller advice in charge of hooking through different error messages to display custom ones.
 */

@RestControllerAdvice
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound() {
        logger.warn("Entity not found");
        return "Not found";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public String handleNotAuthorized() {
        logger.warn("Not authorized");
        return "Not authorized";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleInternalError(Exception e) {
        logger.error("Unhandled Exception in Controller", e);
        return "Internal error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotSuitableTaskException.class)
    public String notSuitableType() {
        logger.error("Task type most likely is not suitable for a result");
        return "Provided task is not suitable for project generation";
    }

}
