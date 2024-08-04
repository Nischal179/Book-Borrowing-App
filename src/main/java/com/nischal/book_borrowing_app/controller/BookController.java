package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.dto.BookRequestDTO;
import com.nischal.book_borrowing_app.dto.BookResponseDTO;
import com.nischal.book_borrowing_app.service.LibraryFacade;
import com.nischal.book_borrowing_app.util.ControllerUtil;
import com.nischal.book_borrowing_app.util.ExceptionUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private LibraryFacade libraryFacade;

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<BookResponseDTO> getBookById(@PathVariable String id) {
        try {
            return (bookService.getBookById(Integer.parseInt(id)));

        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return Optional.empty();
    }

    @PostMapping
    public ResponseEntity <BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
         BookResponseDTO createdBook = bookService.addBook(bookRequestDTO);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable String id, @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        Book updatedBook;
        try {
            BookResponseDTO bookResponseDTO = bookService.updateBook(Integer.parseInt(id), bookRequestDTO);
            return (ResponseEntity.ok(bookResponseDTO));
        }catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {

        try {
            libraryFacade.deleteBook(Integer.parseInt(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}