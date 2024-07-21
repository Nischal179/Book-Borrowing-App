package com.nischal.book_borrowing_app.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO {

    //For request bodies in POST and PUT methods
    @NotBlank(message = "Title is mandatory")
    private String bookName;

    @NotBlank(message = "Author name is mandatory")
    private String author;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    private boolean availableStatus;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}
