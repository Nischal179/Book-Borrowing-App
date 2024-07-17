package com.nischal.book_borrowing_app.dto;

import java.time.LocalDate;

public class BorrowDetailPOJO {

    private String bookName;
    private String authorName;
    private String borrowerName;
    private String email;
    private int borrowId;

    private LocalDate borrowDate;

    public BorrowDetailPOJO(){

    }

    public BorrowDetailPOJO(String bookName, String authorName, String borrowerName, String email, int borrowId, LocalDate borrowDate) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.borrowerName = borrowerName;
        this.email = email;
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getEmail() {
        return email;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}
