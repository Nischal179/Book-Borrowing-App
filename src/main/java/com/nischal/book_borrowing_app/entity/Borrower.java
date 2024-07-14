package com.nischal.book_borrowing_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Borrower {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotBlank(message = "Borrower name is mandatory")
    private String borrowerName;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Mobile number is mandatory")
    private String mobileNo;

    @NotBlank(message = "Email is mandatory")
    private String email;

}
