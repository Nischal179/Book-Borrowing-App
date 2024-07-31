package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Integer> {
    List<Borrow> findByBorrowerId(Integer borrowerId);
    @Query("SELECT b FROM Borrow b WHERE b.book.id = :bookId AND b.returnStatus = false")
    List<Borrow> findByBookId(Integer bookId);

    @Query("SELECT b FROM Borrow b WHERE b.book.id = :bookId AND b.returnStatus = true")
    List<Borrow> findByBookIdAndIsReturnedTrue(Integer bookId);

    @Query("SELECT b FROM Borrow b WHERE b.borrower.id =:borrowerId AND b.returnStatus = false")
    List<Borrow> findByBorrowerIdAndIsReturnedFalse(Integer borrowerId);

    @Query("SELECT b FROM Borrow b WHERE b.borrower.id = :borrowerId AND b.returnStatus = true")
    List<Borrow> findByBorrowerIdAndIsReturnedTrue(Integer borrowerId);
}
