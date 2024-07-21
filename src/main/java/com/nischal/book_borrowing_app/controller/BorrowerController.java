package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.dto.BorrowerRequestDTO;
import com.nischal.book_borrowing_app.dto.BorrowerResponseDTO;
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
    public List<BorrowerResponseDTO> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> getBorrowerById(@PathVariable String id) {
        try {
            BorrowerResponseDTO borrowerResponseDTO = controllerUtil.validateAndGetBorrower(id);
            return ResponseEntity.ok(borrowerResponseDTO);
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<BorrowerResponseDTO> addBorrower(@Valid @RequestBody BorrowerRequestDTO borrowerRequestDTO) {
        BorrowerResponseDTO createdBorrower = borrowerService.addBorrower(borrowerRequestDTO);
        return ResponseEntity.ok(createdBorrower);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> updateBorrower(@PathVariable String id, @RequestBody BorrowerRequestDTO borrowerRequestDTO) {
        try {

            controllerUtil.validateAndGetBorrower(id);
            BorrowerResponseDTO borrowerResponseDTO = borrowerService.updateBorrower(Integer.parseInt(id), borrowerRequestDTO);
            return ResponseEntity.ok(borrowerResponseDTO);

        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable String id) {
        try {
            controllerUtil.validateAndGetBorrower(id);
            borrowerService.deleteBorrower(Integer.parseInt(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}