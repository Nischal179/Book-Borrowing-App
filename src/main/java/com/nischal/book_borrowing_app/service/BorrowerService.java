package com.nischal.book_borrowing_app.service;
import com.nischal.book_borrowing_app.dto.BorrowerRequestDTO;
import com.nischal.book_borrowing_app.dto.BorrowerResponseDTO;
import com.nischal.book_borrowing_app.entity.Borrower;
import com.nischal.book_borrowing_app.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> getBorrowerById(Integer id) {
        return borrowerRepository.findById(id);
    }

    @Transactional
    public Borrower addBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    @Transactional
    public Borrower updateBorrower(Integer id, Borrower borrowerDetails) {
        Borrower borrower = borrowerRepository.findById(id).orElseThrow();
        borrower.setBorrowerName(borrowerDetails.getBorrowerName());
        borrower.setAddress(borrowerDetails.getAddress());
        borrower.setMobileNo(borrowerDetails.getMobileNo());
        borrower.setEmail(borrowerDetails.getEmail());
        return borrowerRepository.save(borrower);
    }

    @Transactional
    public void deleteBorrower(Integer id) {
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