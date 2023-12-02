package test.task.libraryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.task.bookservice.DTO.BookDTO;
import test.task.bookservice.entity.Book;
import test.task.libraryservice.entity.Library;
import test.task.libraryservice.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    //http://localhost:8081/api/library/borrowBook/{bookId}
    @PostMapping("/borrowBook/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable long bookId) {
        libraryService.borrowBook(bookId);
        return ResponseEntity.ok("Book borrowed successfully");
    }


    //http://localhost:8081/api/library/addBookToLibrary/{bookId}
    @PostMapping("/addBookToLibrary/{bookId}")
    public ResponseEntity<String> addBookToLibrary(@PathVariable long bookId) {
        libraryService.addBookToLibrary(bookId);
        return ResponseEntity.ok("Book added to library successfully");
    }







    //http://localhost:8081/api/library/get/getAvailable
    @GetMapping("get/getAvailable")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> books = libraryService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }



    //http://localhost:8081/api/library/get/getAllBorrowed
    @GetMapping("get/getAllBorrowed")
    public ResponseEntity<List<Book>> getAllBorrowedBooks() {
        List<Book> books = libraryService.getAllBorrowedBooks();
        return ResponseEntity.ok(books);
    }



    //http://localhost:8081/api/library/update/books/{bookId}/return
    /*@PutMapping("update/books/{bookId}/return")
    public ResponseEntity<String> returnBook(@PathVariable long bookId) {
        libraryService.returnBook(bookId);
        return ResponseEntity.ok("Book returned successfully");
    }*/



    //http://localhost:8081/api/library/update/books/{bookId}/library/{libraryBookId}/return
    @PutMapping("update/books/{bookId}/library/{libraryBookId}/return")
    public ResponseEntity<String> returnBook(@PathVariable long bookId, @PathVariable long libraryBookId) {
        libraryService.returnBook(bookId, libraryBookId);
        return ResponseEntity.ok("Book returned successfully");
    }



    //http://localhost:8081/api/library/delete/{bookId}
    @DeleteMapping("delete/{bookId}")
    public ResponseEntity<String> deleteBookFromLibrary(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
        return new ResponseEntity<String>("Book deleted from library successfully!.", HttpStatus.OK);
    }

}