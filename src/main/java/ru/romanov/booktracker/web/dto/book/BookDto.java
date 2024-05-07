package ru.romanov.booktracker.web.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.romanov.booktracker.domain.book.Status;
import ru.romanov.booktracker.web.dto.validation.OnCreate;
import ru.romanov.booktracker.web.dto.validation.OnUpdate;


import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookDto {


    @NotNull(message = "Id must be not null", groups = {OnUpdate.class})
    Long id;

    @NotNull(message = "Title must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Title length must be not longer then 255 symbols")
    String title;

    @NotNull(message = "Author must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Author name length must be not longer then 255 symbols")
    String author;

    @Length(max = 512, message = "Description must be not longer then 512 symbols")
    String description;

    Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime expirationTimeToRead;
}
