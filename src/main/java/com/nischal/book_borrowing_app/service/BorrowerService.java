package com.nischal.book_borrowing_app.service;
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

    public void deleteBorrower(Integer id) {
        borrowerRepository.deleteById(id);
    }
}