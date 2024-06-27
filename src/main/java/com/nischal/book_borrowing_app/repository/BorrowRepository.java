package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow,Integer> {
}
