package test.task.libraryservice.entity;


import test.task.bookservice.entity.Book;

import jakarta.persistence.*;
import lombok.Data;



import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book book;

    @Column(name = "borrowed_time")
    private LocalDateTime borrowedTime;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Column(name = "days_period")
    private Long daysPeriod;
}
