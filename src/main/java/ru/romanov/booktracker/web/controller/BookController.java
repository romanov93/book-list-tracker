package ru.romanov.booktracker.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.book.BookImage;
import ru.romanov.booktracker.service.interfaces.BookService;
import ru.romanov.booktracker.web.dto.book.BookDto;
import ru.romanov.booktracker.web.dto.book.BookImageDto;
import ru.romanov.booktracker.web.dto.validation.OnUpdate;
import ru.romanov.booktracker.web.mapper.BookImageMapper;
import ru.romanov.booktracker.web.mapper.BookMapper;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book Controller", description = "Book API")
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    private final BookImageMapper bookImageMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get BookDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#id)")
    public BookDto getById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return bookMapper.toDto(book);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#id)")
    public void deleteById(@PathVariable Long id) {
        bookService.delete(id);
    }

    // Валидируем дто и переходим в метод контроллера только если прошла валидация.
    // Проверяться на валидность будут все аннотации дтошки где есть group OnUpdate.
    @PutMapping
    @Operation(summary = "Update book")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#bookDto.id)")
    public BookDto update(@Validated(OnUpdate.class) @RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book updatedBook = bookService.update(book);
        return bookMapper.toDto(updatedBook);
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "Upload image to book")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#id)")
    public void uploadImage(@PathVariable Long id,
                            @Validated @ModelAttribute BookImageDto bookImageDto) {
        BookImage image = bookImageMapper.toEntity(bookImageDto);
        bookService.uploadImage(id, image);
    }
}
