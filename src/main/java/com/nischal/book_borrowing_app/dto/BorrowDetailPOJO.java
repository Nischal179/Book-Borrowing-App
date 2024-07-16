package com.nischal.book_borrowing_app.dto;

public class BorrowDetailPOJO {

    private String bookName;
    private String authorName;
    private String borrowerName;
    private String email;

    public BorrowDetailPOJO(){

    }

    public BorrowDetailPOJO(String bookName, String authorName, String borrowerName, String email) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.borrowerName = borrowerName;
        this.email = email;
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

    public void setEmail(String email) {
        this.email = email;
    }
}
