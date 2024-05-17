package ru.romanov.booktracker.domain.book;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class Book implements Serializable {

    Long id;

    String title;

    String author;

    String description;

    Status status;

    LocalDateTime expirationDateToRead;
}