package com.nischal.book_borrowing_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerResponseDTO {

    //For response in GET method
    private String borrowerName;
    private String email;
    private int booksBorrowed;
}
