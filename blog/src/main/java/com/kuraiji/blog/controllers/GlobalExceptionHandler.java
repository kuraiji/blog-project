package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.ErrorDto;
import com.kuraiji.blog.exception.RoleNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream().findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation Failed.");
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorDto> handleRoleNotFoundExceptions(RoleNotFoundException ex) {
        Short roleNotFoundId = ex.getId();
        String errorMessage = String.format("Task with ID '%d' not found.", roleNotFoundId);
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
