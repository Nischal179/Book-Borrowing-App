package com.nischal.book_borrowing_app.customError;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({Exception.class, MethodArgumentNotValidException.class})
    public ResponseEntity<CustomErrorResponsePOJO> exceptionHandler (Exception e) {

        CustomErrorResponsePOJO errorResponse = new CustomErrorResponsePOJO();
        HttpStatus status;

        if (e instanceof MethodArgumentNotValidException)
        {

            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put("Error","Bad Request");
//            errors.put("Status Code", String.valueOf(HttpStatus.BAD_REQUEST.value()));
                errors.put(fieldName, errorMessage);
            });
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage("Validation failed");
            errorResponse.setErrors(errors);
            status = HttpStatus.BAD_REQUEST;
        }
        else {

            status = getStatusCode(e);
            errorResponse.setStatus(status.value());
            errorResponse.setMessage(e.getMessage());
        }

        errorResponse.setTimeStamp(LocalDateTime.now().format(dateTimeFormatter));
        return new ResponseEntity<>(errorResponse,status);

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
