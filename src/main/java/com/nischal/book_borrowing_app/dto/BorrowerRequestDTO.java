package com.nischal.book_borrowing_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerRequestDTO {

    //For request bodies in POST and PUT methods
    @NotBlank(message = "Borrower name is mandatory")
    private String borrowerName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address is mandatory")
    private String address;

    // Mobile number should start with 9 and 2nd digit must be either 8 or 7 and other digit should lie in the range
    // 0 to 9
    @Pattern(regexp = "^9[87][0-9]{8}$", message = "Invalid mobile number")
    @NotBlank(message = "Mobile number is mandatory")
    private String mobileNo;

    private int booksBorrowed;
}
