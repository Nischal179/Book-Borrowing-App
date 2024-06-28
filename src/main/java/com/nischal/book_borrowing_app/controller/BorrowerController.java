package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public Optional<Borrower> getBorrowerById(@PathVariable Integer id) {
        return borrowerService.getBorrowerById(id);
    }

    @PostMapping
    public Borrower addBorrower(@RequestBody Borrower borrower) {
        return borrowerService.addBorrower(borrower);
    }

    @PutMapping("/{id}")
    public Borrower updateBorrower(@PathVariable Integer id, @RequestBody Borrower borrowerDetails) {
        return borrowerService.updateBorrower(id, borrowerDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrower(@PathVariable Integer id) {
        borrowerService.deleteBorrower(id);
    }
}