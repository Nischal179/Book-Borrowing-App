package com.nischal.book_borrowing_app.customError;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomErrorResponsePOJO> handleException (Exception e) {

        CustomErrorResponsePOJO errorResponse = new CustomErrorResponsePOJO();
        HttpStatus status = getStatusCode(e);
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,status);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponsePOJO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put("Error","Bad Request");
//            errors.put("Status Code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
            errors.put(fieldName, errorMessage);
        });

        CustomErrorResponsePOJO errorResponse = new CustomErrorResponsePOJO();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed");
        errorResponse.setErrors(errors);
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public HttpStatus getStatusCode(Exception e) {

        if(e.getMessage().toLowerCase().contains("bad request")) {

            return HttpStatus.BAD_REQUEST;

        } else if (e.getMessage().toLowerCase().contains("not found")) {

            return HttpStatus.NOT_FOUND;

        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
