package ru.romanov.booktracker.web.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import ru.romanov.booktracker.domain.book.Status;
import ru.romanov.booktracker.web.dto.validation.OnCreate;
import ru.romanov.booktracker.web.dto.validation.OnUpdate;


import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Schema(description = "Book DTO")
public class BookDto {

    @Schema(description = "Book id", example = "66")
    @NotNull(message = "Id must be not null", groups = {OnUpdate.class})
    Long id;

    @Schema(description = "Book title", example = "Perdido Street Station")
    @NotNull(message = "Title must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Title length must be not longer then 255 symbols")
    String title;

    @Schema(description = "Book author", example = "China Mieville")
    @NotNull(message = "Author must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Author name length must be not longer then 255 symbols")
    String author;

    @Schema(description = "Book short description", example = "Dark fantasy british bestseller")
    @Length(max = 512, message = "Description must be not longer then 512 symbols")
    String description;

    @Schema(description = "Book reading status", example = "READ_STARTED")
    Status status;

    @Schema(description = "Datetime when reading should be complete")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime expirationTimeToRead;

    @Schema(description = "Name of images with book")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<String> images;
}