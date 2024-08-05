package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.customError.CustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryFacade {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowerService borrowerService;

    @Transactional
    public void deleteBook(Integer id) {
        if (borrowService.hasAssociatedBorrowRecordsForBook(id)) {
            throw new CustomException("Conflict: Cannot delete book as it is associated with borrow records.");
        }
        borrowService.deleteReturnedBorrowsForBook(id);
        bookService.deleteBook(id);
    }

    @Transactional
    public void deleteBorrower(Integer id) {
        if (borrowService.hasUnreturnedBorrowsForBorrower(id)) {
            throw new CustomException("Conflict: Cannot delete borrower associated with borrow record");
        }
        borrowService.deleteReturnedBorrowsForBorrower(id);
        borrowerService.deleteBorrower(id);
    }
}
