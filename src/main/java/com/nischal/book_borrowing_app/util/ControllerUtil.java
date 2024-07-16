package com.nischal.book_borrowing_app.util;

import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.service.BookService;
import com.nischal.book_borrowing_app.service.BorrowService;
import com.nischal.book_borrowing_app.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                throw new CustomException("Not found");
            }

            return (theBook.get());
        } catch (Exception e) {

            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

}
