package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.customError.CustomException;
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

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {

        int bookId;
        Optional<Book> theBook;
        try {
            bookId = Integer.parseInt(id);
            theBook = bookService.getBookById(bookId);
            if (theBook.isEmpty()) {
                throw new CustomException("Not found");
            }

            return ResponseEntity.ok(theBook.get());

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
            bookId = Integer.parseInt(id);
            if (bookService.getBookById(bookId).isEmpty()) {
                throw new CustomException("Not found");
            }
            updatedBook = bookService.updateBook(bookId, bookDetails);
            return (ResponseEntity.ok(updatedBook));
        }catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        int bookId;
        try {
            bookId = Integer.parseInt(id);
            if (bookService.getBookById(bookId).isEmpty()) {
                throw new CustomException("Not found");
            }
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ExceptionUtil.handleException(id,e);
        }
        return null;
    }
}