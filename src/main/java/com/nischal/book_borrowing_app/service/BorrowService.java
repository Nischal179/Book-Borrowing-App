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
import java.util.NoSuchElementException;
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

    public boolean hasAssociatedBorrowRecordsForBook(Integer bookId) {
        List<Borrow> borrows = borrowRepository.findByBookIdAndIsReturnedFalse(bookId);
        return !borrows.isEmpty();
    }

    public void deleteReturnedBorrowsForBook(Integer bookId) {
        List<Borrow> returnedBorrows = borrowRepository.findByBookIdAndIsReturnedTrue(bookId);
        for (Borrow borrow : returnedBorrows) {
            borrowRepository.deleteById(borrow.getBorrowID());
        }
    }

    public boolean hasUnreturnedBorrowsForBorrower(Integer borrowerId) {
        return !borrowRepository.findByBorrowerIdAndIsReturnedFalse(borrowerId).isEmpty();
    }

    public void deleteReturnedBorrowsForBorrower(Integer borrowerId) {
        List<Borrow> returnedBorrows = borrowRepository.findByBorrowerIdAndIsReturnedTrue(borrowerId);
        for (Borrow borrow : returnedBorrows) {
            borrowRepository.deleteById(borrow.getBorrowID());
        }
    }

    @Transactional
    public BorrowResponseDTO recordBorrow(Integer borrowerId, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Not Found: No book found with the provided id"));
        Borrower borrower = borrowerRepository.findById(borrowerId).orElseThrow(() -> new NoSuchElementException("Not Found: No borrower found with the provided id"));

        validateBookAvailability(book);

        updateBookAndBorrower(book, borrower);

        Borrow borrow = createBorrowRecord(book, borrower);
        return convertToDto(borrowRepository.save(borrow));
    }

    private void validateBookAvailability(Book book) {
        if (book.getQuantity() <= 0 || !book.getAvailableStatus()) {
            throw new CustomException("Conflict: Book is not available for borrowing");
        }
    }

    private void updateBookAndBorrower(Book book, Borrower borrower) {
        borrower.setBooksBorrowed(borrower.getBooksBorrowed() + 1);
        book.setQuantity(book.getQuantity() - 1);
        if (book.getQuantity() == 0) {
            book.setAvailableStatus(false);
        }
        bookRepository.save(book);
    }

    private Borrow createBorrowRecord(Book book, Borrower borrower) {
        Borrow borrow = new Borrow();
        borrow.setBorrower(borrower);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDateExpected(LocalDate.now().plusDays(15));
        borrow.setReturnStatus(false);
        return borrow;
    }


//    @Transactional
//    public BorrowResponseDTO updateBorrow(Integer id, Integer borrowerId, Integer bookId) {
//        Borrow borrow = borrowRepository.findById(id).orElseThrow();
//        borrow.setBorrower(borrowerRepository.findById(borrowerId).orElseThrow());
//        borrow.setBook(bookRepository.findById(bookId).orElseThrow());
//        return convertToDto(borrowRepository.save(borrow));
//    }


    // TODO: Return date cannot be more than today's date
    // TODO: Check if Late Return Fee is paid
    @Transactional
    public BorrowResponseDTO updateBorrow(Integer id, LocalDate returnDateActual) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not Found: No borrow record found with" +
                " the provided id"));

        validateReturnStatus(borrow);
        validateReturnDate(borrow, returnDateActual);

        Borrower borrower = borrow.getBorrower();
        Book book = borrow.getBook();
        borrow.setReturnDateActual(returnDateActual);
        borrower.setBooksBorrowed(borrower.getBooksBorrowed() - 1);
        borrow.setReturnStatus(true);
        book.setQuantity(book.getQuantity()+1);

        if (returnDateActual.isAfter(borrow.getReturnDateExpected())) {
            int lateReturnDays = returnDateActual.compareTo(borrow.getReturnDateExpected());
            borrow.setLateReturnFee(lateReturnDays);
        }

        return convertToDto(borrowRepository.save(borrow));
    }

    private void validateReturnStatus(Borrow borrow) {
        if (borrow.getReturnDateActual() != null && borrow.isReturnStatus()) {
            throw new CustomException("Conflict: Book has already been returned");
        }
    }

    private void validateReturnDate(Borrow borrow, LocalDate returnDateActual) {
        if (returnDateActual.isBefore(borrow.getBorrowDate())) {
            throw new CustomException("Bad Request: Return date cannot be before borrow date");
        }
    }


    @Transactional
    public void deleteBorrow(Integer id) {
        Borrow borrow = borrowRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Not Found: Data " +
                "for corresponding id :- " + id));
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
        Optional<Borrow> borrow = borrowRepository.findById(id);
        if (borrow.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        }
        else {
            return borrow.map(this::convertToDto);
        }
    }

    public List<BorrowResponseDTO> getBorrowsByBorrowerId(Integer borrowerId) {
        List<Borrow> borrow = borrowRepository.findByBorrowerId(borrowerId);
        if (borrow.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + borrowerId);
        }
        else {
            return borrow.stream().map(this::convertToDto).collect(Collectors.toList());
        }

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
        borrowResponseDTO.setExpectedReturnDate(borrow.getReturnDateExpected());
        borrowResponseDTO.setActualReturnDate(borrow.getReturnDateActual());
        borrowResponseDTO.setReturnStatus(borrow.isReturnStatus());
        return borrowResponseDTO;
    }
}



