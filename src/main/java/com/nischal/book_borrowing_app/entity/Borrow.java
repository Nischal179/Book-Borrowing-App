package com.nischal.book_borrowing_app.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "borrow_seq_generator", sequenceName = "borrow_seq", allocationSize = 1)
    private Integer borrowID;

    @ManyToOne
    @JoinColumn(name = "borrowerID", referencedColumnName = "Id")
    private Borrower borrower;

    @ManyToOne
    @JoinColumn(name = "bookID", referencedColumnName = "bookID")
    private Book book;

    private LocalDate borrowDate;

    private LocalDate returnDateExpected;

    private LocalDate returnDateActual;
    private double lateReturnFee;
    private boolean returnStatus;
}
