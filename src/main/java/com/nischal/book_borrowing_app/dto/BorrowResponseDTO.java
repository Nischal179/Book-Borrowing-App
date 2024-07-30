package com.nischal.book_borrowing_app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BorrowResponseDTO {

    private int borrowId;
    private String borrowerName;
    private String email;
    private String bookName;
    private String authorName;
    private double price;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private boolean returnStatus;

}
