package org.fetch.exercise.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.fetch.exercise.exception.ReceiptNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalHttpExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalHttpExceptionHandler.class);
    private static final String ERRORS_KEY = "errors";

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ReceiptNotFoundException ex) {
        LOG.error("Exception: {}", ex.getMessage(), ex);
        final Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERRORS_KEY, ex.getMessage());
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        LOG.error("Exception: {}", ex.getMessage(), ex);
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public final ResponseEntity<Object> handleDateValidationErrors(DateTimeParseException ex) {
        LOG.error("Exception: {}", ex.getMessage(), ex);
        String error = "Invalid Date format: Date format should be a valid YYYY-MM-DD";
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERRORS_KEY, error);
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidFormatException.class})
    public final ResponseEntity<Object> handleFormatValidationErrors(InvalidFormatException ex) {
        LOG.error("Exception: {}", ex.getMessage(), ex);
        String error = "Invalid format for value: " + ex.getValue();
        if (ex.getTargetType().equals(LocalDate.class)) {
            error += ". Date format should be a valid YYYY-MM-DD";
        } else {
            error += ". Expected type: " + ex.getTargetType().getSimpleName();
        }
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERRORS_KEY, error);
        return new ResponseEntity<>(errorMap, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Map<String, String>> getErrorsMap(List<ObjectError> errors) {
        Map<String, Map<String, String>> errorResponse = new HashMap<>();
        Map<String, String> errorFields = new HashMap<>();
        for (ObjectError error : errors) {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorFields.put(fieldName, message);
        }

        errorResponse.put(ERRORS_KEY, errorFields);

        return errorResponse;
    }
}
