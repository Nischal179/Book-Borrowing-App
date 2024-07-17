package com.nischal.book_borrowing_app.controller;

import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowService;
import com.nischal.book_borrowing_app.util.ControllerUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrows")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ControllerUtil controllerUtil;

    @GetMapping
    public List<Borrow> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    @GetMapping("/{id}")
    public Optional<Borrow> getBorrowById(@PathVariable Integer id) {
        return borrowService.getBorrowById(id);
    }

    @GetMapping("/borrower/{borrowerId}")
    public List<Borrow> getBorrowsByBorrowerId(@PathVariable Integer borrowerId) {
        return borrowService.getBorrowsByBorrowerId(borrowerId);
    }

    @PostMapping
    public Borrow recordBorrow(@Valid @RequestParam String borrowerId, @RequestParam String bookId) {
        Book book = controllerUtil.validateAndGetBook(bookId);
        Borrower borrower = controllerUtil.validateAndGetBorrower(borrowerId);
        return borrowService.recordBorrow(borrower.getId(), book.getBookId());
    }

    @PutMapping("/{id}")
    public Borrow updateBorrow(@PathVariable Integer id, @RequestParam Integer borrowerId, @RequestParam Integer bookId) {
        return borrowService.updateBorrow(id, borrowerId, bookId);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrow(@PathVariable Integer id) {
        borrowService.deleteBorrow(id);
    }
}
