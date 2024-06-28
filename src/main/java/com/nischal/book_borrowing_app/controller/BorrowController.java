package com.nischal.book_borrowing_app.controller;

import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrows")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

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
    public Borrow recordBorrow(@RequestParam Integer borrowerId, @RequestParam Integer bookId) {
        return borrowService.recordBorrow(borrowerId, bookId);
    }
}
