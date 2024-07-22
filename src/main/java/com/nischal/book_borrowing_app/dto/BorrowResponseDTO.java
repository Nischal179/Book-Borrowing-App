package com.nischal.book_borrowing_app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BorrowResponseDTO {

    private String bookName;
    private String authorName;
    private String borrowerName;
    private String email;
    private double price;
    private int borrowId;

    private LocalDate borrowDate;

    private LocalDate returnDate;

}
