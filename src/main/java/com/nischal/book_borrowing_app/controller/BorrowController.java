package com.nischal.book_borrowing_app.controller;

import com.nischal.book_borrowing_app.dto.BookResponseDTO;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowService;
import com.nischal.book_borrowing_app.util.ControllerUtil;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Borrow> getBorrowById(@PathVariable String id) {
        try {
            Borrow borrow = controllerUtil.validateAndGetBorrow(id);
            return (ResponseEntity.ok(borrow));
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @GetMapping("/borrower/{borrowerId}")
    public List<Borrow> getBorrowsByBorrowerId(@PathVariable Integer borrowerId) {
        return borrowService.getBorrowsByBorrowerId(borrowerId);
    }

    @PostMapping
    public Borrow recordBorrow(@Valid @RequestParam String borrowerId, @RequestParam String bookId) {
        Borrower borrower = controllerUtil.validateAndGetBorrower(borrowerId);
        BookResponseDTO bookResponseDTO = controllerUtil.validateAndGetBook(bookId);
        return borrowService.recordBorrow(borrower.getId(), Integer.parseInt(bookId));
    }

    @PutMapping("/{id}")
    public Borrow updateBorrow(@PathVariable String id, @RequestParam String borrowerId, @RequestParam String bookId) {
        controllerUtil.validateAndGetBorrow(id);
        controllerUtil.validateAndGetBorrower(borrowerId);
        controllerUtil.validateAndGetBook(bookId);
        return borrowService.updateBorrow(Integer.parseInt(id), Integer.parseInt(borrowerId), Integer.parseInt(bookId));
    }

    @DeleteMapping("/{id}")
    public void deleteBorrow(@PathVariable String id) {
        controllerUtil.validateAndGetBorrow(id);
        borrowService.deleteBorrow(Integer.parseInt(id));
    }
}
