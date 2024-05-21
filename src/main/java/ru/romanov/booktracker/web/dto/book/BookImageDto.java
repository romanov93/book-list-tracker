package ru.romanov.booktracker.web.dto.book;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookImageDto {

    @NotNull(message = "Image must be not null")
    MultipartFile file;

}
