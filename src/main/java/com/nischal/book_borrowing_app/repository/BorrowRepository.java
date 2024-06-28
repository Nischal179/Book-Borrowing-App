package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Integer> {
    List<Borrow> findByBorrowerId(Integer borrowerId);
}
