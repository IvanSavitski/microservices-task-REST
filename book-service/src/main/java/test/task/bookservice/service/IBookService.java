package test.task.bookservice.service;





import reactor.core.publisher.Mono;
import test.task.bookservice.entity.Book;
import java.util.List;
import java.util.Optional;



/*
public interface IBookService {
    BookDTO save(BookDTO bookDTO);

    BookDTO findById(Long id);

    void delete(Long id);

    List<BookDTO> findAll();
}
*/


public interface IBookService {

    // GET
    List<Book> getAllBooks();
    Optional<Book> getBookById(long id);
    Optional<Book> getBookByIsbn(String isbn);



    // POST
    Book addBook(Book book);

    List<Book> addBooks(List<Book> books);


    // PUT
    Book updateBook(Book book, long id);
    //Book updateBookByIsbn(Book book, String isbn);



    //DELETE
    void deleteBook(long id);
}