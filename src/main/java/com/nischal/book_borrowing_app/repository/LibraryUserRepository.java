package com.nischal.book_borrowing_app.repository;

import com.nischal.book_borrowing_app.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Integer> {

    LibraryUser findByUsername(String username);
}
