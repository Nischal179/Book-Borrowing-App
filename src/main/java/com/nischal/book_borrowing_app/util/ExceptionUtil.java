package com.nischal.book_borrowing_app.util;

import com.nischal.book_borrowing_app.customError.CustomException;

import java.util.NoSuchElementException;

public class ExceptionUtil {

    public static void handleException(String id, Exception e)
    {
        if (e instanceof NumberFormatException) {
            throw new NumberFormatException("Bad Request: ID must be a number :- " + id);
        } else if (e instanceof NoSuchElementException) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        } else if (e instanceof CustomException) {
            throw new CustomException(e.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            throw new IllegalArgumentException(e.getMessage());
        } else {
            throw new RuntimeException("Bad Request");
        }
    }
}
