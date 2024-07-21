package com.nischal.book_borrowing_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerResponseDTO {

    //For response in GET method

    /*  You should not include bookId in your response
    but for my ease during development I have included it  */
    private int borrowerId;

    private String borrowerName;
    private String email;
    private int booksBorrowed;
}
