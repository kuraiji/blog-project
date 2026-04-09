package com.kuraiji.blog.controllers;

import com.kuraiji.blog.domain.dto.ErrorDto;
import com.kuraiji.blog.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsExceptions(BadCredentialsException ex) {
        ErrorDto errorDto = new ErrorDto("Incorrect username or password");
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleIllegalStateExceptions(IllegalStateException ex) {
        ErrorDto errorDto = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundExceptions(UserNotFoundException ex) {
        Long userNotFoundId = ex.getId();
        String errorMessage = String.format("User with ID '%d' not found.", userNotFoundId);
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseNotInitializedException.class)
    public ResponseEntity<ErrorDto> handleDatabaseNotInitializedExceptions(DatabaseNotInitializedException ex) {
        ErrorDto errorDto = new ErrorDto("Database isn't initialized yet.");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthorizationInvalidException.class)
    public ResponseEntity<ErrorDto> handleAuthorizationInvalidExceptions(AuthorizationInvalidException ex) {
        ErrorDto errorDto = new ErrorDto("You are not authorized to access this");
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UriNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUriNotFoundExceptions(UriNotFoundException ex) {
        ErrorDto errorDto = new ErrorDto("Not a valid resource");
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorDto> handlePostNotFoundExceptions(PostNotFoundException ex) {
        UUID id = ex.getId();
        String errorMessage = String.format("Post with ID '%s' not found.", id);
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCommentNotFoundExceptions(CommentNotFoundException ex) {
        UUID id = ex.getId();
        String errorMessage = String.format("Comment with ID '%s' not found.", id);
        ErrorDto errorDto = new ErrorDto(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
