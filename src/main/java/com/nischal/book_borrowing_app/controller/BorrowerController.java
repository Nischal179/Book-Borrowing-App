package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowerService;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable String id) {
        int borrowerId;
        Optional<Borrower> borrower;
        try {
            borrowerId = Integer.parseInt(id);
            borrower = borrowerService.getBorrowerById(borrowerId);
            if (borrower.isEmpty()) {
                throw new CustomException("Not found");
            }
            return ResponseEntity.ok(borrower.get());
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Borrower> addBorrower(@Valid @RequestBody Borrower borrower) {
        Borrower createdBorrower = borrowerService.addBorrower(borrower);
        return ResponseEntity.ok(createdBorrower);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Borrower> updateBorrower(@PathVariable String id, @RequestBody Borrower borrowerDetails) {
        int borrowerId;
        Borrower updatedBorrower;
        try {
            borrowerId = Integer.parseInt(id);
            if (borrowerService.getBorrowerById(borrowerId).isEmpty())
            {
                throw new CustomException("Not Found");
            }
            updatedBorrower = borrowerService.updateBorrower(borrowerId, borrowerDetails);
            return ResponseEntity.ok(updatedBorrower);
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable String id) {
        int borrowerId;
        try {
            borrowerId = Integer.parseInt(id);
            if (borrowerService.getBorrowerById(borrowerId).isEmpty())
            {
                throw new CustomException("Not Found");
            }
            borrowerService.deleteBorrower(borrowerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}