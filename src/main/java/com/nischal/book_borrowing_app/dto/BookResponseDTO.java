package com.nischal.book_borrowing_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDTO {

    //For response in GET method
    private String bookName;
    private String author;
    private Double price;
    private Integer quantity;
    private boolean availableStatus;
}
