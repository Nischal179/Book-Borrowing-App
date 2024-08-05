package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.dto.BorrowerRequestDTO;
import com.nischal.book_borrowing_app.dto.BorrowerResponseDTO;
import com.nischal.book_borrowing_app.service.BorrowerService;
import com.nischal.book_borrowing_app.service.LibraryFacade;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
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

    @Autowired
    private LibraryFacade libraryFacade;

    @GetMapping
    public List<BorrowerResponseDTO> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{id}")
    public Optional<BorrowerResponseDTO> getBorrowerById(@PathVariable String id) {
        try {
            return (borrowerService.getBorrowerById(Integer.parseInt(id)));
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return Optional.empty();
    }

    @PostMapping
    public ResponseEntity<BorrowerResponseDTO> addBorrower(@Valid @RequestBody BorrowerRequestDTO borrowerRequestDTO) {
        BorrowerResponseDTO createdBorrower = borrowerService.addBorrower(borrowerRequestDTO);
        return ResponseEntity.ok(createdBorrower);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowerResponseDTO> updateBorrower(@PathVariable String id, @Valid @RequestBody BorrowerRequestDTO borrowerRequestDTO) {
        try {
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
            libraryFacade.deleteBorrower(Integer.parseInt(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}