package com.nischal.book_borrowing_app.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO {

    private String bookName;
    private String author;
    private Double price;
    private boolean availableStatus;
}
