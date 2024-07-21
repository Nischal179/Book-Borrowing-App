package com.nischal.book_borrowing_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerRequestDTO {

    //For request bodies in POST and PUT methods
    @NotBlank(message = "Borrower name is mandatory")
    private String borrowerName;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Mobile number is mandatory")
    private String mobileNo;

    private int booksBorrowed;
}
