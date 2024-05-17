package ru.romanov.booktracker.web.mapper;

import org.mapstruct.Mapper;
import ru.romanov.booktracker.domain.book.BookImage;
import ru.romanov.booktracker.web.dto.book.BookImageDto;

@Mapper(componentModel = "spring")
public interface BookImageMapper extends Mappable<BookImage, BookImageDto> {
}
