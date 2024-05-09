package ru.romanov.booktracker.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.service.interfaces.BookService;
import ru.romanov.booktracker.service.interfaces.UserService;
import ru.romanov.booktracker.web.dto.book.BookDto;
import ru.romanov.booktracker.web.dto.user.UserDto;
import ru.romanov.booktracker.web.dto.validation.OnCreate;
import ru.romanov.booktracker.web.dto.validation.OnUpdate;
import ru.romanov.booktracker.web.mapper.BookMapper;
import ru.romanov.booktracker.web.mapper.UserMapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return userMapper.toDto(user);
    }

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/books")
    public List<BookDto> getBooksByUserId(@PathVariable Long id) {
        List<Book> usersBooks = bookService.getAllByUserId(id);
        return bookMapper.toDto(usersBooks);
    }

    @PostMapping("/{id}/books")
    public BookDto createBook(@PathVariable (name = "id") Long userId,
                              @Validated(OnCreate.class) @RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book createdBook = bookService.create(book, userId);
        return bookMapper.toDto(createdBook);
    }


}