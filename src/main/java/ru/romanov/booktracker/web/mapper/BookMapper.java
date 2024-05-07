package ru.romanov.booktracker.web.mapper;

import org.mapstruct.Mapper;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.web.dto.book.BookDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto dto);

    List<BookDto> toDto(List<Book> books);
}