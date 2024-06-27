package com.nischal.book_borrowing_app.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer borrowID;

    @ManyToOne
    @JoinColumn(name = "borrowerID")
    private Borrower borrower;

    @ManyToOne
    @JoinColumn(name = "bookID")
    private Book book;

    private LocalDate borrowDate;

}
