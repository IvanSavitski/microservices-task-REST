package test.task.bookservice.repository;



import test.task.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, String> {

    //List<Book> findAll();

    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);

    //Book addBook(Book book);

    // Book updateBook(Book book, long id);



    /*List<Book> findByTitle(String title);
    List<Book> findByDescription(String description);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);*/


    void deleteById(long id);
}
