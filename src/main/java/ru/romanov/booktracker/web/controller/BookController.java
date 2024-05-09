package ru.romanov.booktracker.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.service.interfaces.BookService;
import ru.romanov.booktracker.web.dto.book.BookDto;
import ru.romanov.booktracker.web.dto.validation.OnUpdate;
import ru.romanov.booktracker.web.mapper.BookMapper;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return bookMapper.toDto(book);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.delete(id);
    }

    // Валидируем дто и переходим в метод контроллера только если прошла валидация.
    // Проверяться на валидность будут все аннотации дтошки где есть group OnUpdate.
    @PutMapping
    public BookDto update(@Validated(OnUpdate.class) @RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book updatedBook = bookService.update(book);
        return bookMapper.toDto(updatedBook);
    }
}