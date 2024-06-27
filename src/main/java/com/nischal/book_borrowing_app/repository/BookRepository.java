package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
