package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable Integer id) {
        Optional<Borrower> borrower = borrowerService.getBorrowerById(id);
        if(borrower.isPresent()) {
            return ResponseEntity.ok(borrower.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Borrower> addBorrower(@RequestBody Borrower borrower) {
        Borrower createdBorrower = borrowerService.addBorrower(borrower);
        return ResponseEntity.ok(createdBorrower);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Borrower> updateBorrower(@PathVariable Integer id, @RequestBody Borrower borrowerDetails) {
        try {
            Borrower updatedBorrower = borrowerService.updateBorrower(id, borrowerDetails);
            return ResponseEntity.ok(updatedBorrower);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Integer id) {
        try {
            borrowerService.deleteBorrower(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}