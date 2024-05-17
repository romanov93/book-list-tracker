package ru.romanov.booktracker.web.mapper;

import org.mapstruct.Mapper;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.web.dto.book.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper extends Mappable<Book, BookDto> {

}