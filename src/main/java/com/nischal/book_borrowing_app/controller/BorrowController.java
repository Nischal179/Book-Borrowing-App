package com.nischal.book_borrowing_app.controller;

import com.nischal.book_borrowing_app.dto.BorrowRequestDTO;
import com.nischal.book_borrowing_app.dto.BorrowResponseDTO;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.service.BorrowService;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrows")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping
    public List<BorrowResponseDTO> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    @GetMapping("/{id}")
    public Optional<BorrowResponseDTO> getBorrowById(@PathVariable String id) {
        try {
            return (borrowService.getBorrowById(Integer.parseInt(id)));
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return Optional.empty() ;
    }

    @GetMapping("/borrower/{borrowerId}")
    public List<BorrowResponseDTO> getBorrowsByBorrowerId(@PathVariable String borrowerId) {
        try {
            return borrowService.getBorrowsByBorrowerId(Integer.parseInt(borrowerId));
        } catch (Exception e) {
            ExceptionUtil.handleException(borrowerId,e);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<BorrowResponseDTO> recordBorrow(@Valid @RequestParam String borrowerId,
                                                         @RequestParam String bookId,
                                           UriComponentsBuilder ucb) {
        BorrowResponseDTO createdBorrow =  borrowService.recordBorrow(Integer.parseInt(borrowerId), Integer.parseInt(bookId));
        URI locationOfNewBorrow = ucb
                .path("borrows/{id}")
                .buildAndExpand(createdBorrow.getBorrowId())
                .toUri();
        return ResponseEntity.created(locationOfNewBorrow).build();
    }

    @PutMapping("/{id}")
    public BorrowResponseDTO updateBorrow(@PathVariable String id, @RequestBody BorrowRequestDTO borrowRequestDTO) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate returnDateActual = LocalDate.parse(borrowRequestDTO.getReturnDateActual(), dateTimeFormatter);
            return borrowService.updateBorrow(Integer.parseInt(id), returnDateActual);
        } catch (Exception e)
        {
            ExceptionUtil.handleException(id, e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteBorrow(@PathVariable String id) {
        try {
            borrowService.deleteBorrow(Integer.parseInt(id));
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
    }
}
