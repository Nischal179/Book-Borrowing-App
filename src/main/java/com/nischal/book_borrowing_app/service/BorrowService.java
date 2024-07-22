package com.nischal.book_borrowing_app.service;

import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.dto.BorrowResponseDTO;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.repository.BookRepository;
import com.nischal.book_borrowing_app.repository.BorrowRepository;
import com.nischal.book_borrowing_app.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Transactional
    public BorrowResponseDTO recordBorrow(Integer borrowerId, Integer bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow();
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow();
        // Check if the book can be borrowed
        if (book.getQuantity() <= 0 || !book.getAvailableStatus()) {
            throw new CustomException("Conflict: Book is not available for borrowing");
        }
        if(borrower.getBooksBorrowed()==0)
        {
            borrower.setBooksBorrowed(1);
        } else {
            borrower.setBooksBorrowed(borrower.getBooksBorrowed()+1);
        }
        Borrow borrow = new Borrow();
        borrow.setBorrower(borrower);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDateExpected(LocalDate.now().plusDays(15));
        borrow.setReturnStatus(false);

        // Decrease the quantity of the borrowed book
        book.setQuantity(book.getQuantity() - 1);
        if (book.getQuantity()==0)
        {
            book.setAvailableStatus(false);
        }
        bookRepository.save(book);

        return convertToDto(borrowRepository.save(borrow));
    }

    @Transactional
    public BorrowResponseDTO updateBorrow(Integer id, Integer borrowerId, Integer bookId) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow();
        borrow.setBorrower(borrowerRepository.findById(borrowerId).orElseThrow());
        borrow.setBook(bookRepository.findById(bookId).orElseThrow());
        return convertToDto(borrowRepository.save(borrow));
    }

    @Transactional
    public void deleteBorrow(Integer id) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow();
        if (borrow.isReturnStatus() && borrow.getLateReturnFee()==0)
        {
            borrowRepository.delete(borrow);
        }
        else {
            throw new CustomException("Conflict: Borrower has not returned the borrowed book or has pending late return fee");
        }
    }

    public List<BorrowResponseDTO> getAllBorrows() {
        return borrowRepository.findAll().stream()
                .map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<BorrowResponseDTO> getBorrowById(Integer id) {
        return borrowRepository.findById(id)
                .map(this::convertToDto);
    }

    public List<BorrowResponseDTO> getBorrowsByBorrowerId(Integer borrowerId) {
        return borrowRepository.findByBorrowerId(borrowerId)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BorrowResponseDTO convertToDto(Borrow borrow) {
        BorrowResponseDTO borrowResponseDTO = new BorrowResponseDTO();
        borrowResponseDTO.setBorrowId(borrow.getBorrowID());
        borrowResponseDTO.setBorrowerName(borrow.getBorrower().getBorrowerName());
        borrowResponseDTO.setEmail(borrow.getBorrower().getEmail());
        borrowResponseDTO.setBookName(borrow.getBook().getBookName());
        borrowResponseDTO.setAuthorName(borrow.getBook().getAuthor());
        borrowResponseDTO.setPrice(borrow.getBook().getPrice());
        borrowResponseDTO.setBorrowDate(borrow.getBorrowDate());
        borrowResponseDTO.setReturnDate(borrow.getReturnDateExpected());

        return borrowResponseDTO;
    }
}



