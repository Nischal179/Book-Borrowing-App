package com.nischal.book_borrowing_app.util;

import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BookService;
import com.nischal.book_borrowing_app.service.BorrowService;
import com.nischal.book_borrowing_app.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ControllerUtil {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private BorrowService borrowService;

    public Book validateAndGetBook(String id) {
        int bookId;
        Optional<Book> theBook;

        try {

            bookId = Integer.parseInt(id);
            theBook = bookService.getBookById(bookId);

            if (theBook.isEmpty()) {
                throw new NoSuchElementException("Not found");
            }

            return (theBook.get());
        } catch (Exception e) {

            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    public Borrower validateAndGetBorrower(String id) {
        int borrowerId;
        Optional<Borrower> borrower;

        try {
            borrowerId = Integer.parseInt(id);
            borrower = borrowerService.getBorrowerById(borrowerId);
            if (borrower.isEmpty()) {
                throw new NoSuchElementException("Not Found");
            }
            return (borrower.get());
        } catch (Exception e)
        {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}
