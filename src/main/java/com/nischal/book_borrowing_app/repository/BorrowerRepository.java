package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Integer> {

    Optional<Borrower> findByEmail(String email);
}
