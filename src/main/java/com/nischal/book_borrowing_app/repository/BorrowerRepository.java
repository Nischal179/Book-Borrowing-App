package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower,Integer> {
}
