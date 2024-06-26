package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.repository.BookRepository;
import com.nischal.book_borrowing_app.repository.BorrowRepository;
import com.nischal.book_borrowing_app.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Borrow recordBorrow(Integer borrowerId, Integer bookId) {
        Borrow borrow = new Borrow();
        borrow.setBorrower(borrowerRepository.findById(borrowerId).orElseThrow());
        Book book = bookRepository.findById(bookId).orElseThrow();

        // Check if the book is available
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available for borrowing.");
        }

        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());

        // Decrease the quantity of the borrowed book
        book.setQuantity(book.getQuantity() - 1);

        // Set availability to false if quantity is zero
        if (book.getQuantity()-1 <= 0) {
            book.setAvailableStatus(false);
        }

        bookRepository.save(book);
        return borrowRepository.save(borrow);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setBookName(bookDetails.getBookName());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setQuantity(bookDetails.getQuantity());
        book.setAvailableStatus(bookDetails.getAvailableStatus());
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}