package com.nischal.book_borrowing_app.customError;

import java.util.Map;

public class CustomErrorResponsePOJO {

    private int status;
    private String message;
    private long timeStamp;

    private Map<String, String> errors; //For validation errors

    public CustomErrorResponsePOJO() {

    }

    public CustomErrorResponsePOJO(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String,String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
