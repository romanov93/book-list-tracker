package ru.romanov.booktracker.domain.book;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "books")
@Data
@FieldDefaults(level = PRIVATE)
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String author;

    String description;

    @Enumerated(value = EnumType.STRING)
    Status status;

    LocalDateTime expirationDateToRead;
}