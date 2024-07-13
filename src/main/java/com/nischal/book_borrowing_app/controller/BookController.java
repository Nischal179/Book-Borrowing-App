package com.nischal.book_borrowing_app.controller;
import com.nischal.book_borrowing_app.customError.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nischal.book_borrowing_app.entity.Book;
import com.nischal.book_borrowing_app.service.BookService;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {

        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()){
            return ResponseEntity.ok(book.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity <Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book bookDetails) {
        int bookId;
        Book updatedBook;
        try {
            bookId = Integer.parseInt(id);
            if (bookService.getBookById(bookId).isEmpty()) {
                throw new CustomException("Not found");

            }
            updatedBook = bookService.updateBook(bookId, bookDetails);

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Bad Request: ID must be a number:  "+id);
        }catch (CustomException e) {
            throw new RuntimeException("Not Found: No data found for corresponding id: "+id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Bad Request");
        }
        return (updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new CustomException("not found");
        }

    }
}