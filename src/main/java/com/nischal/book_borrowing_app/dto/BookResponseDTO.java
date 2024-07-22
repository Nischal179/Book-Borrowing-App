package com.nischal.book_borrowing_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDTO {

    //For response in GET method


    /*  You should not include bookId in your response
    but for my ease during development I have included it  */
    private int bookId;

    private String bookName;
    private String author;
    private Double price;
    private Integer quantity;
    private boolean availableStatus;
}
