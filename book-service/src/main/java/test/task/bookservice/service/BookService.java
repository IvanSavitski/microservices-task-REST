package test.task.bookservice.service;


/*
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.ProductRequest;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
*/

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import test.task.bookservice.entity.Book;
import test.task.bookservice.exception.ResourceNotFoundException;
import test.task.bookservice.repository.BookRepository;

import java.util.List;
import java.util.Optional;





@Service
@Transactional
//@RequiredArgsConstructor
@Slf4j
public class BookService implements IBookService {

    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        super();
        this.bookRepository = bookRepository;
    }





    // GET
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        /*Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()) {
            return optionalBook.get(); //get book from optionalBook
        }else {
            throw new ResourceNotFoundException("Employee", "Id", id);
        }*/

        //return bookRepository.findById(id).orElseThrow(() ->
        //        new ResourceNotFoundException("Book", "Id", id));


        //Optional<Book> optionalBook = bookRepository.findById(id);
        //return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


        //Optional<Book> optionalBook = bookRepository.findById(id);
        //return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book", "Id", id));

        Optional<Book> optionalBook = bookRepository.findById(id);
        return Optional.ofNullable(optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book", "Isbn", id)));

    }


    @Override
    //public Book getBookByIsbn(String isbn)
    public Optional<Book> getBookByIsbn(String isbn) {

        /*Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        if(optionalBook.isPresent()) {
            return Optional.of(optionalBook.get());
        }else {
            throw new ResourceNotFoundException("Book", "Isbn", isbn);
        }*/

        //return Optional.ofNullable(bookRepository.findById(isbn).orElseThrow(() ->
        //       new ResourceNotFoundException("Book", "Isbn", isbn)));

        //Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        //return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        return Optional.ofNullable(optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book", "Isbn", isbn)));
    }






    // POST
    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> addBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }



    // PUT
    @Override
    public Book updateBook(Book book, long id) {

        // we need to check whether employee with given id is exist in DB or not
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book", "Id", id));

        // обновление информации о книге
        existingBook.setIsbn(book.getIsbn());
        existingBook.setTitle(book.getTitle());
        existingBook.setGenre(book.getGenre());
        existingBook.setDescription(book.getDescription());
        existingBook.setAuthor(book.getAuthor());

        // save existing employee to DB
        bookRepository.save(existingBook);
        return existingBook;


        /*Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            // обновление информации о книге
            existingBook.setIsbn(book.getIsbn());
            existingBook.setTitle(book.getTitle());
            existingBook.setGenre(book.getGenre());
            existingBook.setDescription(book.getDescription());
            existingBook.setAuthor(book.getAuthor());

            return ResponseEntity.ok(bookRepository.save(existingBook));
        } else {
            return ResponseEntity.notFound().build();
        }*/

    }



    // DELETE
    @Override
    public void deleteBook(long id) {

        // check whether a employee exist in a DB or not
        bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book", "Id", id));
        bookRepository.deleteById(id);
    }


}
