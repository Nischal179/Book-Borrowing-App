package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.customError.CustomException;
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

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        try {
            Book book = controllerUtil.validateAndGetBook(id);
            return ResponseEntity.ok(book);

        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }

        return null;
    }

    @PostMapping
    public ResponseEntity <Book> addBook(@Valid @RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book bookDetails) {
        int bookId;
        Book updatedBook;
        try {
            Book book = controllerUtil.validateAndGetBook(id);
            updatedBook = bookService.updateBook(book.getBookId(), bookDetails);
            return (ResponseEntity.ok(updatedBook));
        }catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {

        try {
            Book book = controllerUtil.validateAndGetBook(id);
            bookService.deleteBook(Integer.parseInt(id));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}