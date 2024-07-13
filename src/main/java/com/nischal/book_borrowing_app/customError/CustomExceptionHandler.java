package com.nischal.book_borrowing_app.customError;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    public HttpStatus getStatusCode(Exception e) {

        if(e.getMessage().equalsIgnoreCase("bad request")) {

            return HttpStatus.BAD_REQUEST;

        } else if (e.getMessage().equalsIgnoreCase("not found")) {

            return HttpStatus.NOT_FOUND;

        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
