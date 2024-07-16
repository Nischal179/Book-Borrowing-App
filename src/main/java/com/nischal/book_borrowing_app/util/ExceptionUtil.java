package com.nischal.book_borrowing_app.util;

import com.nischal.book_borrowing_app.customError.CustomException;

public class ExceptionUtil {

    public static void handleException(String id, Exception e)
    {
        if (e instanceof NumberFormatException) {
            throw new NumberFormatException("Bad Request: ID must be a number: " + id);
        } else if (e instanceof CustomException) {
            throw new CustomException("Not Found: Data for corresponding id: " + id);
        }
        else {
            throw new IllegalArgumentException("Bad Request");
        }
    }
}
