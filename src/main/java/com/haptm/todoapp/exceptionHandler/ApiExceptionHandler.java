package com.haptm.todoapp.exceptionHandler;

import com.haptm.todoapp.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
        return new ErrorMessage(10000, ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        return new ErrorMessage(10010, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ErrorMessage(10100, ex.getMessage());
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage handleUserExistException(UserExistException ex, WebRequest request) {
        return new ErrorMessage(10101, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {
        return new ErrorMessage(10102, ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest request) {
        return new ErrorMessage(10103, ex.getMessage());
    }

    @ExceptionHandler(CategoryExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage handleCategoryExistException(CategoryExistException ex, WebRequest request) {
        return new ErrorMessage(10104, ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        return new ErrorMessage(10105, ex.getMessage());
    }
}
