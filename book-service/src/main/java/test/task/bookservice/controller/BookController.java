package test.task.bookservice.controller;




import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
import test.task.bookservice.DTO.BookDTO;
import test.task.bookservice.entity.Book;
import test.task.bookservice.service.BookService;
//import test.task.libraryservice.service.LibraryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/books")
@Slf4j
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;

    ///
    //private final LibraryService libraryService;
    ///

    //private final WebClient webClient;




    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper  /*WebClient.Builder webClientBuilder*/) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;

        ///
        //this.libraryService = libraryService;
        ///

       // this.webClient = webClientBuilder.build();
    }


    // http://localhost:8082/api/books/get/getAll
    @GetMapping("get/getAll")
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        //return bookService.getAllBooks();
    }



    // http://localhost:8082/api/books/get/getById/{id}
    @GetMapping("get/getById/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable (value = "id") Long id) {

        //return new ResponseEntity<Book>(bookService.getBookById(id), HttpStatus.OK);

        //THIS
        return bookService.getBookById(id)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        //INSTEAD THIS
        /*
        return bookService.getBookById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        */
    }



    // http://localhost:8082/api/books/get/getByIsbn/{isbn}
    @GetMapping("get/getByIsbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable (value = "isbn") String isbn) {

        //return new ResponseEntity<Book>(bookService.getBookByIsbn(isbn), HttpStatus.OK);

        //THIS
        return bookService.getBookByIsbn(isbn)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        //INSTEAD THIS
        /*
        return bookService.getBookByIsbn(isbn)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        */


        // DELETING
        // Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        // return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }




    //http://localhost:8082/api/books/create/book
    @PostMapping("create/book")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {

        //THIS
        log.info("Creating Book...");
        Book book = modelMapper.map(bookDTO, Book.class);
        Book savedBook = bookService.addBook(book);
        BookDTO savedBookDTO = modelMapper.map(savedBook, BookDTO.class);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }


    //http://localhost:8082/api/books/create/books
    @PostMapping("create/books")
    public ResponseEntity<List<BookDTO>> addBooks(@RequestBody List<BookDTO> bookDTOs) {
        log.info("Creating Books...");

        List<Book> books = bookDTOs.stream()
                .map(bookDTO -> modelMapper.map(bookDTO, Book.class))
                .collect(Collectors.toList());

        List<Book> savedBooks = bookService.addBooks(books);

        List<BookDTO> savedBookDTOs = savedBooks.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(savedBookDTOs, HttpStatus.CREATED);
    }



    /* @PostMapping("/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        // Создание Book объекта из BookDTO
        log.info("Creating Book...");
        Book book = modelMapper.map(bookDTO, Book.class);

        // Отправка асинхронного POST-запроса в сервис Library
        Mono<Library> responseMono = webClient.build()
                .post()
                .uri("http://library-service/books")
                .body(Mono.just(book), Book.class)
                .retrieve()
                .bodyToMono(LibraryBook.class);

        // Ожидание ответа и обработка его
        Library createdBook = responseMono.block();

        // Обновление ID книги в сервисе Book
        book.setLibraryId(createdBook.getId());

        // Сохранение книги в сервисе Book
        Book savedBook = bookService.addBook(book);

        // Преобразование сохраненной книги обратно в BookDTO
        BookDTO savedBookDTO = modelMapper.map(savedBook, BookDTO.class);

        // Возврат ответа с созданной книгой
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }*/







    //http://localhost:8082/api/books/update/{id}
    @PutMapping("update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        Book updatedBook = bookService.updateBook(book, id);
        BookDTO updatedBookDTO = modelMapper.map(updatedBook, BookDTO.class);
        return new ResponseEntity<>(updatedBookDTO, HttpStatus.OK);

        //INSTEAD THIS
        //return new ResponseEntity<Book>(bookService.updateBook(book, id), HttpStatus.OK);
    }



    //http://localhost:8082/api/books/delete/{id}
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<String>("Book deleted successfully!.", HttpStatus.OK);
    }
}