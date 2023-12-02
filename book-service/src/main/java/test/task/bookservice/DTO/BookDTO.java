package test.task.bookservice.DTO;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;
    private boolean isBorrowed;
}

