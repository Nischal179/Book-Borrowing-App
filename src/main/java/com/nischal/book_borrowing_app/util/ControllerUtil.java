package com.nischal.book_borrowing_app.util;

import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.dto.BookResponseDTO;
import com.nischal.book_borrowing_app.dto.BorrowResponseDTO;
import com.nischal.book_borrowing_app.dto.BorrowerResponseDTO;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
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

    public BookResponseDTO validateAndGetBook(String id) {
        int bookId;
        Optional<BookResponseDTO> theBookResponseDTO;

        try {

            bookId = Integer.parseInt(id);
            theBookResponseDTO = bookService.getBookById(bookId);

            if (theBookResponseDTO.isEmpty()) {
                throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
            }

            return (theBookResponseDTO.get());
        } catch (Exception e) {

            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    public BorrowerResponseDTO validateAndGetBorrower(String id) {
        int borrowerId;
        Optional<BorrowerResponseDTO> theBorrowerResponseDTO;

        try {
            borrowerId = Integer.parseInt(id);
            theBorrowerResponseDTO = borrowerService.getBorrowerById(borrowerId);
            if (theBorrowerResponseDTO.isEmpty()) {
                throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
            }
            return (theBorrowerResponseDTO.get());
        } catch (Exception e)
        {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    public BorrowResponseDTO validateAndGetBorrow(String id) {
        int borrow_id;
        Optional<BorrowResponseDTO> borrowResponseDTO;
        try {
            borrow_id = Integer.parseInt(id);
            borrowResponseDTO = borrowService.getBorrowById(borrow_id);
            if (borrowResponseDTO.isEmpty()) {
                throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
            }
            return (borrowResponseDTO.get());
        } catch (Exception e)
        {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}
