package com.nischal.book_borrowing_app.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO {

    //For request bodies in POST and PUT methods
    private String bookName;
    private String author;
    private Double price;
//    private boolean availableStatus;
    private Integer quantity;
}
