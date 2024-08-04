package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.dto.BookRequestDTO;
import com.nischal.book_borrowing_app.dto.BookResponseDTO;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<BookResponseDTO> getBookById(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        }
        else {
            return book.map(this::convertToDto);
        }
    }

    @Transactional
    public BookResponseDTO addBook(BookRequestDTO bookRequestDTO) {
        Book book = convertToEntity(bookRequestDTO);
        if (book.getQuantity()==0)
        {
            book.setAvailableStatus(false);
        }
        else if (book.getQuantity()>0) {
            book.setAvailableStatus(true);
        }
        return convertToDto(bookRepository.save(book));
    }

//    @Transactional
//    public Borrow recordBorrow(Integer borrowerId, Integer bookId) {
//        Borrow borrow = new Borrow();
//        borrow.setBorrower(borrowerRepository.findById(borrowerId).orElseThrow());
//        Book book = bookRepository.findById(bookId).orElseThrow();
//
//        // Check if the book is available
//        if (book.getQuantity() <= 0) {
//            throw new CustomException("Book is not available for borrowing.");
//        }
//
//        borrow.setBook(book);
//        borrow.setBorrowDate(LocalDate.now());
//
//        // Decrease the quantity of the borrowed book
//        book.setQuantity(book.getQuantity() - 1);
//
//        // Set availability to false if quantity is zero
//        if (book.getQuantity()-1 <= 0) {
//            book.setAvailableStatus(false);
//        }
//
//        bookRepository.save(book);
//        return borrowRepository.save(borrow);
//    }

    @Transactional
    public BookResponseDTO updateBook(Integer id, BookRequestDTO bookRequestDTO) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        }
        else {
            Book existingbook = book.get();
            if (bookRequestDTO.getQuantity() == 0) {
                existingbook.setAvailableStatus(false);
            } else if (bookRequestDTO.getQuantity()>0) {
                existingbook.setAvailableStatus(true);
            }
            existingbook.setBookName(bookRequestDTO.getBookName());
            existingbook.setAuthor(bookRequestDTO.getAuthor());
            existingbook.setPrice(bookRequestDTO.getPrice());
            existingbook.setQuantity(bookRequestDTO.getQuantity());
            return convertToDto(bookRepository.save(existingbook));
        }
    }

    @Transactional
    public void deleteBook(Integer id) {

        List<Borrow> borrows = borrowRepository.findByBookId(id);
        if (borrows.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        } else if(!borrowRepository.findByBorrowerIdAndIsReturnedFalse(id).isEmpty()) {
            throw new CustomException("Conflict: Cannot delete book associated with borrow record");
        } else if (!borrowRepository.findByBookIdAndIsReturnedTrue(id).isEmpty()) {
            List<Borrow> returnedBorrows = borrowRepository.findByBookIdAndIsReturnedTrue(id);
            if (!returnedBorrows.isEmpty()) {
                for(Borrow borrow : borrows) {
                    Integer borrowId = borrow.getBorrowID();
                    borrowRepository.deleteById(borrowId);
                }
            }
        }
        bookRepository.deleteById(id);
    }

    private Book convertToEntity(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setBookName(bookRequestDTO.getBookName());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setPrice(bookRequestDTO.getPrice());
        book.setQuantity(bookRequestDTO.getQuantity());
        return book;
    }

    private BookResponseDTO convertToDto(Book book) {
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setBookId(book.getBookId());
        bookResponseDTO.setBookName(book.getBookName());
        bookResponseDTO.setAuthor(book.getAuthor());
        bookResponseDTO.setQuantity(book.getQuantity());
        bookResponseDTO.setPrice(book.getPrice());
        bookResponseDTO.setAvailableStatus(book.getAvailableStatus());
        return bookResponseDTO;
    }
}