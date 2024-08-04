package com.nischal.book_borrowing_app.service;
import com.nischal.book_borrowing_app.customError.CustomException;
import com.nischal.book_borrowing_app.dto.BorrowerRequestDTO;
import com.nischal.book_borrowing_app.dto.BorrowerResponseDTO;
import com.nischal.book_borrowing_app.entity.Borrow;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.repository.BorrowRepository;
import com.nischal.book_borrowing_app.repository.BorrowerRepository;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    public List<BorrowerResponseDTO> getAllBorrowers() {
        return borrowerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<BorrowerResponseDTO> getBorrowerById(Integer id) {
        Optional<Borrower> borrower =  borrowerRepository.findById(id);
        if (borrower.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        } else {
            return borrower.map(this::convertToDto);
        }
    }

    @Transactional
    public BorrowerResponseDTO addBorrower(BorrowerRequestDTO borrowerRequestDTO) {
        Borrower borrower = convertToEntity(borrowerRequestDTO);
        Optional<Borrower> existingBorrowerOpt = borrowerRepository.findByEmail(borrowerRequestDTO.getEmail());
        try {
            if(existingBorrowerOpt.isPresent())
            {
                throw new CustomException("Conflict: Borrower associated with the provided email already exists");
            }
            else {
                borrower.setBooksBorrowed(0);
                return convertToDto(borrowerRepository.save(borrower));
            }
        }
        catch (Exception e){
            ExceptionUtil.handleException(String.valueOf(existingBorrowerOpt.get().getId()),e);
        }
        return null;
    }

    @Transactional
    public BorrowerResponseDTO updateBorrower(Integer id, BorrowerRequestDTO borrowerRequestDTO) {
        Optional<Borrower> borrower = borrowerRepository.findById(id);
        if (borrower.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        }
        else {
            Borrower extractedBorrower = borrower.get();
            extractedBorrower.setBorrowerName(borrowerRequestDTO.getBorrowerName());
            extractedBorrower.setAddress(borrowerRequestDTO.getAddress());
            extractedBorrower.setMobileNo(borrowerRequestDTO.getMobileNo());
            extractedBorrower.setEmail(borrowerRequestDTO.getEmail());
            return convertToDto(borrowerRepository.save(extractedBorrower));
        }
    }

    @Transactional
    public void deleteBorrower(Integer id) {
        Optional<Borrower> borrower = borrowerRepository.findById(id);
        if (borrower.isEmpty()) {
            throw new NoSuchElementException("Not Found: Data for corresponding id :- " + id);
        } else if (!borrowRepository.findByBorrowerIdAndIsReturnedFalse(id).isEmpty()) {
            throw new CustomException("Conflict: Cannot delete borrower associated with borrow record");
        }
        else if (!borrowRepository.findByBorrowerIdAndIsReturnedTrue(id).isEmpty()) {
            List<Borrow> borrows = borrowRepository.findByBorrowerIdAndIsReturnedTrue(id);
            for (Borrow borrow : borrows) {
                Integer borrowId = borrow.getBorrowID();
                borrowRepository.deleteById(borrowId);
            }
        }
        borrowerRepository.deleteById(id);
    }

    private Borrower convertToEntity(BorrowerRequestDTO borrowerRequestDTO) {
        Borrower borrower = new Borrower();
        borrower.setBorrowerName(borrowerRequestDTO.getBorrowerName());
        borrower.setAddress(borrowerRequestDTO.getAddress());
        borrower.setEmail(borrowerRequestDTO.getEmail());
        borrower.setMobileNo(borrowerRequestDTO.getMobileNo());
        return borrower;
    }

    private BorrowerResponseDTO convertToDto(Borrower borrower) {
        BorrowerResponseDTO borrowerResponseDTO = new BorrowerResponseDTO();
        borrowerResponseDTO.setBorrowerId(borrower.getId());
        borrowerResponseDTO.setBorrowerName(borrower.getBorrowerName());
        borrowerResponseDTO.setEmail(borrower.getEmail());
        borrowerResponseDTO.setBooksBorrowed(borrower.getBooksBorrowed());
        return borrowerResponseDTO;
    }
}