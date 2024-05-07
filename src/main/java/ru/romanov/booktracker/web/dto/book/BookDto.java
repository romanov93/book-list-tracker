package ru.romanov.booktracker.web.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.springframework.format.annotation.DateTimeFormat;
import ru.romanov.booktracker.domain.book.Status;


import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookDto {


    Long id;

    String title;

    String author;

    String description;

    Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime expirationTimeToRead;
}
