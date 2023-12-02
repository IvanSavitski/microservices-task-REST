package test.task.libraryservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import test.task.bookservice.entity.Book;
import test.task.bookservice.exception.ResourceNotFoundException;

import test.task.bookservice.repository.BookRepository;
import test.task.libraryservice.entity.Library;
import test.task.libraryservice.exception.BookLibraryException;
import test.task.libraryservice.repository.LibraryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;



/*
@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final LibraryBookRepository libraryBookRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, LibraryBookRepository libraryBookRepository) {
        this.bookRepository = bookRepository;
        this.libraryBookRepository = libraryBookRepository;
    }

    public void addBookToLibrary(long bookId) {
        // Найти книгу по ID
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            // Создать новую запись в библиотеке для данной книги
            LibraryBook libraryBook = new LibraryBook();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setReturnTime(LocalDateTime.now().plusDays(14)); // Здесь можно настроить срок возврата книги

            libraryBookRepository.save(libraryBook);
        } else {
            throw new ResourceNotFoundException("Book", "Id", bookId);
        }
    }
}
*/

@Service
@Slf4j
public class LibraryService {

    private static final Long DAYS = 14L;
    private final LibraryRepository libraryBookRepository;
    private final BookRepository bookRepository;
    //private final RestTemplate restTemplate;
    private final WebClient webClient;


    @Autowired
    public LibraryService(LibraryRepository libraryBookRepository, WebClient.Builder webClientBuilder, BookRepository bookRepository /*RestTemplate restTemplate*/) {
        this.libraryBookRepository = libraryBookRepository;
        this.webClient = webClientBuilder.build();
        //this.restTemplate = restTemplate;
        this.bookRepository = bookRepository;
    }

    /*public void addBookToLibrary(long bookId) {
        // Отправить запрос на получение информации о книге по ее ID
        ResponseEntity<Book> response = restTemplate.getForEntity("http://localhost:8080/api/books/" + bookId, Book.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            Book book = response.getBody();

            // Создать новую запись в библиотеке для данной книги
            Library libraryBook = new Library();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setDaysPeriod(DAYS);
            libraryBook.setReturnTime(LocalDateTime.now().plusDays(libraryBook.getDaysPeriod()));

            libraryBookRepository.save(libraryBook);
        } else {
            throw new ResourceNotFoundException("Library", "Id", bookId);
        }
    }*/


    /*
    public void addBookToLibrary(long bookId) {
        // Отправить запрос на получение информации о книге по ее ID
        Mono<Book> bookMono = webClient.get()
                .uri("http://localhost:8080/api/books/{bookId}", bookId)
                .retrieve()
                .bodyToMono(Book.class);

        bookMono.subscribe(book -> {
            // Создать новую запись в библиотеке для данной книги
            Library libraryBook = new Library();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setDaysPeriod(DAYS);
            libraryBook.setReturnTime(LocalDateTime.now().plusDays(libraryBook.getDaysPeriod()));

            libraryBookRepository.save(libraryBook);
        }, error -> {
            throw new ResourceNotFoundException("Library", "Id", bookId);
        });
    } */



    // WORKING METHOD BUT NOT ALL LOGICS INSIDE
    /*public void borrowBook(long bookId) {
        // Отправить запрос на получение информации о книге по ее ID
        Mono<Book> bookMono = webClient.get()
                .uri("http://localhost:8082/api/books/{bookId}", bookId)
                .retrieve()
                .bodyToMono(Book.class);

        bookMono.subscribe(book -> {
            // Создать новую запись в библиотеке для данной книги
            Library libraryBook = new Library();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setDaysPeriod(DAYS);
            libraryBook.setReturnTime(LocalDateTime.now().plusDays(libraryBook.getDaysPeriod()));

            book.setBorrowed(true);
            libraryBookRepository.save(libraryBook);
        }, error -> {
            throw new ResourceNotFoundException("Library", "Id", bookId);
        });
    }*/









    // WORKING METHOD BUT NOT ALL LOGICS INSIDE 2 вариант
    /*public void borrowBook(long bookId) {
        Optional<Library> libraryBookMatch = libraryBookRepository.findById(bookId);

        if (libraryBookMatch.isPresent()) {
            log.info("Book is already borrowed");
            throw new BookLibraryException("Book is already borrowed", bookId);
        }

        // Отправить запрос на получение информации о книге по ее ID
        Mono<Book> bookMono = webClient.get()
                .uri("http://localhost:8082/api/books/get/getById/{bookId}", bookId)
                .retrieve()
                .bodyToMono(Book.class);

        bookMono.subscribe(book -> {
            // Создать новую запись в библиотеке для данной книги
            Library libraryBook = new Library();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setDaysPeriod(DAYS);
            libraryBook.setReturnTime(LocalDateTime.now().plusDays(libraryBook.getDaysPeriod()));

            book.setBorrowed(true);
            libraryBookRepository.save(libraryBook);

            // Запустить планировщик задач для возврата книги через заданный период времени
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> returnBook(libraryBook.getId()), libraryBook.getDaysPeriod(), TimeUnit.DAYS);
        }, error -> {
            throw new ResourceNotFoundException("Library", "bookId", bookId);
        });
    }*/














    public void addBookToLibrary(long bookId) {
        Optional<Library> libraryBookMatch = libraryBookRepository.findById(bookId);


        if (libraryBookMatch.isPresent()) {
            log.info("Book is already added to library");
            throw new BookLibraryException("Book is already added to library", bookId);
        }


        // Отправить запрос на получение информации о книге по ее ID
        Mono<Book> bookMono = webClient.get()
                .uri("http://localhost:8082/api/books/get/getById/{bookId}", bookId)
                .retrieve()
                .bodyToMono(Book.class);

        bookMono.subscribe(book -> {
            // Создать новую запись в библиотеке для данной книги
            Library libraryBook = new Library();
            libraryBook.setBook(book);
            libraryBook.setBorrowedTime(null);
            libraryBook.setDaysPeriod(DAYS);
            libraryBook.setReturnTime(null);
            libraryBookRepository.save(libraryBook);
        }, error -> {
            throw new ResourceNotFoundException("Library", "bookId", bookId);
        });
    }



    public void borrowBook(long bookId) {
        Optional<Library> libraryBookMatch = libraryBookRepository.findById(bookId);
        Optional<Book> bookMatch = bookRepository.findById(bookId);

        if (libraryBookMatch.isPresent()) {
            log.info("Book is exist in library");
        }

        //if isBorrowed is true
        if(bookMatch.get().isBorrowed()) {
            log.info("Book is already borrowed");
            throw new BookLibraryException("Book is already borrowed", bookId);
        }

        /*if (libraryBook.getReturnTime() != null && libraryBook.getReturnTime().isAfter(LocalDateTime.now())) {
            throw new BookLibraryException("Book is already borrowed", bookId);
        }*/


        // Отправить запрос на получение информации о книге по ее ID
        Mono<Book> bookMono = webClient.get()
                .uri("http://localhost:8082/api/books/get/getById/{bookId}", bookId)
                .retrieve()
                .bodyToMono(Book.class);



        bookMono.subscribe(book -> {
            // Обновить информацию о книге в библиотеке
            Library libraryBook = libraryBookMatch.get();
            libraryBook.setBorrowedTime(LocalDateTime.now());
            libraryBook.setDaysPeriod(DAYS);
            //libraryBook.setReturnTime(LocalDateTime.now().plusDays(libraryBook.getDaysPeriod()));
            libraryBook.setReturnTime(LocalDateTime.now().plusSeconds(libraryBook.getDaysPeriod()));
            Book bookExmp = bookMatch.get();
            bookExmp.setBorrowed(true);

            libraryBookRepository.save(libraryBook);
            bookRepository.save(bookExmp);

            // Запустить планировщик задач для возврата книги через заданный период времени
            //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            //executorService.schedule(() -> returnBook(bookExmp.getId(), libraryBook.getId()), libraryBook.getDaysPeriod(), TimeUnit.DAYS);

            // Автоматический возврат книги через заданный период времени
            CompletableFuture.delayedExecutor(libraryBook.getDaysPeriod(), TimeUnit.SECONDS)
                    .execute(() -> returnBook(bookExmp.getId(), libraryBook.getId()));
        }, error -> {
            throw new ResourceNotFoundException("Library", "bookId", bookId);
        });
    }






    public void returnBook(long bookId, long libraryBookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ResourceNotFoundException("Book", "bookId", bookId));;
        Library libraryBook = libraryBookRepository.findById(libraryBookId).orElseThrow(
                () -> new ResourceNotFoundException("Library Book", "libraryBookId", libraryBookId));
        //Book book = libraryBook.getBook();

        book.setBorrowed(false);

        libraryBook.setBook(book);
        libraryBook.setBorrowedTime(null);
        libraryBook.setDaysPeriod(DAYS);
        libraryBook.setReturnTime(null);


        libraryBookRepository.save(libraryBook);
        bookRepository.save(book);

        //libraryBookRepository.delete(libraryBook);
    }






    public List<Book> getAvailableBooks() {
        List<Library> libraryBooks = libraryBookRepository.findAll();
        List<Book> availableBooks = libraryBooks.stream()
                // .filter(libraryBook -> libraryBook.getReturnTime().isBefore(LocalDateTime.now()))
                .filter(libraryBook -> libraryBook.getBorrowedTime() == null && libraryBook.getReturnTime() == null)
                .map(Library::getBook)
                .collect(Collectors.toList());

        //return libraryBooks;
        return availableBooks;
    }


    public List<Book> getAllBorrowedBooks() {
        List<Library> libraryBooks = libraryBookRepository.findAll();
        List<Book> borrowedBooks = libraryBooks.stream()
                //.filter(libraryBook -> libraryBook.getReturnTime().isAfter(LocalDateTime.now()))
                .filter(libraryBook -> libraryBook.getBorrowedTime() != null && libraryBook.getReturnTime() != null)
                .map(Library::getBook)
                .collect(Collectors.toList());

        //return libraryBooks;
        return borrowedBooks;
    }

    /*private boolean isBorrowed(Book book, List<Library> borrowedBooks) {
        return borrowedBooks.stream()
                .anyMatch(library -> library.getBook().equals(book));
    }*/



    public void deleteBook(long bookId) {

        // check whether a employee exist in a DB or not
        libraryBookRepository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Library", "bookId", bookId));
        libraryBookRepository.deleteById(bookId);
    }


}
