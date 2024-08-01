package com.nischal.book_borrowing_app.controller;

import com.nischal.book_borrowing_app.dto.BookResponseDTO;
import com.nischal.book_borrowing_app.dto.BorrowRequestDTO;
import com.nischal.book_borrowing_app.dto.BorrowResponseDTO;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.service.BorrowService;
import com.nischal.book_borrowing_app.util.ControllerUtil;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ControllerUtil controllerUtil;

    @GetMapping
    public List<BorrowResponseDTO> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowResponseDTO> getBorrowById(@PathVariable String id) {
        try {
            BorrowResponseDTO borrowResponseDTO = controllerUtil.validateAndGetBorrow(id);
            return (ResponseEntity.ok(borrowResponseDTO));
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
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
    public BorrowResponseDTO recordBorrow(@Valid @RequestParam String borrowerId, @RequestParam String bookId) {
        controllerUtil.validateAndGetBorrower(borrowerId);
        controllerUtil.validateAndGetBook(bookId);
        return borrowService.recordBorrow(Integer.parseInt(borrowerId), Integer.parseInt(bookId));
    }

//    @PutMapping("/{id}")
//    public BorrowResponseDTO updateBorrow(@PathVariable String id, @RequestParam String borrowerId, @RequestParam String bookId) {
//        controllerUtil.validateAndGetBorrow(id);
//        controllerUtil.validateAndGetBorrower(borrowerId);
//        controllerUtil.validateAndGetBook(bookId);
//        return borrowService.updateBorrow(Integer.parseInt(id), Integer.parseInt(borrowerId), Integer.parseInt(bookId));
//    }
    @PutMapping("/{id}")
    public BorrowResponseDTO updateBorrow(@PathVariable String id, @RequestBody BorrowRequestDTO borrowRequestDTO) {
        try {
            controllerUtil.validateAndGetBorrow(id);
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
            controllerUtil.validateAndGetBorrow(id);
            borrowService.deleteBorrow(Integer.parseInt(id));
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
    }
}
