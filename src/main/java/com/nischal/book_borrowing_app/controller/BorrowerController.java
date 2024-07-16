package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowerService;
import com.nischal.book_borrowing_app.util.ControllerUtil;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private ControllerUtil controllerUtil;

    @GetMapping
    public List<Borrower> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable String id) {
        try {
            Borrower borrower = controllerUtil.validateAndGetBorrower(id);
            return ResponseEntity.ok(borrower);
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
        Borrower updatedBorrower;
        try {

            Borrower borrower = controllerUtil.validateAndGetBorrower(id);
            updatedBorrower = borrowerService.updateBorrower(borrower.getId(), borrowerDetails);
            return ResponseEntity.ok(updatedBorrower);

        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable String id) {
        try {
            Borrower borrower = controllerUtil.validateAndGetBorrower(id);
            borrowerService.deleteBorrower(borrower.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}