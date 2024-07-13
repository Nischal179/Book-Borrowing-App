package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.repository.BookRepository;
import com.nischal.book_borrowing_app.repository.BorrowRepository;
import com.nischal.book_borrowing_app.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Transactional
    public Borrow recordBorrow(Integer borrowerId, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();

        // Check if the book can be borrowed
        if (book.getQuantity() <= 0 || !book.getAvailableStatus()) {
            throw new RuntimeException("Book is not available for borrowing");
        }
        Borrow borrow = new Borrow();
        borrow.setBorrower(borrowerRepository.findById(borrowerId).orElseThrow());
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());

        // Decrease the quantity of the borrowed book
        book.setQuantity(book.getQuantity() - 1);
        if (book.getQuantity()==0)
        {
            book.setAvailableStatus(false);
        }
        bookRepository.save(book);

        return borrowRepository.save(borrow);
    }

    public Borrow updateBorrow(Integer id, Integer borrowerId, Integer bookId) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow();
        borrow.setBorrower(borrowerRepository.findById(borrowerId).orElseThrow());
        borrow.setBook(bookRepository.findById(bookId).orElseThrow());
        return borrowRepository.save(borrow);
    }

    public void deleteBorrow(Integer id) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow();
        borrowRepository.delete(borrow);
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Optional<Borrow> getBorrowById(Integer id) {
        return borrowRepository.findById(id);
    }

    public List<Borrow> getBorrowsByBorrowerId(Integer borrowerId) {
        return borrowRepository.findByBorrowerId(borrowerId);
    }
}



