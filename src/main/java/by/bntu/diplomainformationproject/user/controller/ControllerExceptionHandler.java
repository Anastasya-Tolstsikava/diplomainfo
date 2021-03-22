package by.bntu.diplomainformationproject.user.controller;


import by.bntu.diplomainformationproject.dto.ErrorDto;
import by.bntu.diplomainformationproject.service.exception.EntryIsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String VALIDATION_EXCEPTION_MSG = "Validation exception";

//    @ExceptionHandler(VerificationTokenException.class)
//    protected ResponseEntity<ErrorDto> handleVerificationTokenException(VerificationTokenException e) {
//        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.name(), e.getMessage());
//        log.debug("Exception = {}", errorDto);
//        return ResponseEntity.badRequest().body(errorDto);
//    }

    @ExceptionHandler(EntryIsNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleEntryNotFoundException(EntryIsNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND.name(), e.getMessage());
        log.debug("Exception = {}", errorDto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, List<String>> errorMap = parseBindingResult(bindingResult);
        ErrorDto errorDto =
                new ErrorDto(HttpStatus.BAD_REQUEST.name(), VALIDATION_EXCEPTION_MSG, errorMap);
        log.debug("Exception = {}", errorDto);
        return ResponseEntity.badRequest().body(errorDto);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    protected ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException e) {
//        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.name(), e.getMessage());
//        log.debug("Exception = {}", errorDto);
//        return ResponseEntity.badRequest().body(errorDto);
//    }

//    @ExceptionHandler(DontConfirmedEmail.class)
//    protected ResponseEntity<ErrorDto> handleDontConfirmedEmail(DontConfirmedEmail e) {
//        ErrorDto errorDto = new ErrorDto(HttpStatus.CONFLICT.name(), e.getMessage());
//        log.debug("Exception = {}", errorDto);
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
//    }

    private Map<String, List<String>> parseBindingResult(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, List<String>> errorMap = new HashMap<>();
        for (FieldError error : fieldErrors) {
            if (!errorMap.containsKey(error.getField())) {
                errorMap.put(error.getField(), new ArrayList<>());
            }
            errorMap.get(error.getField()).add(error.getDefaultMessage());
        }
        return errorMap;
    }
}
