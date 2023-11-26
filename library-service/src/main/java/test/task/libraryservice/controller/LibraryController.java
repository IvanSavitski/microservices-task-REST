package test.task.libraryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.bookservice.entity.Book;
import test.task.libraryservice.service.LibraryService;

import java.util.List;


@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/books/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable long bookId) {
        libraryService.borrowBook(bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> books = libraryService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

}