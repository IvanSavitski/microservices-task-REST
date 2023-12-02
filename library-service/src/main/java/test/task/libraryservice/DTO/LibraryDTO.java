package test.task.libraryservice.DTO;


import lombok.Data;
import test.task.bookservice.entity.Book;
import java.time.LocalDateTime;



@Data
public class LibraryDTO {
    private Long id;
    private Book book;
    private LocalDateTime borrowedTime;
    private LocalDateTime returnTime;
    private Long daysPeriod;
}
