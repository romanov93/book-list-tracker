package ru.romanov.booktracker.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/{id}")
    @QueryMapping(name = "userById")
    @Operation(summary = "Get UserDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable @Argument Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PutMapping
    @MutationMapping(name = "updateUser")
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userDto.id)")
    public UserDto update(@Validated(OnUpdate.class)
                          @RequestBody @Argument UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @MutationMapping(name = "deleteUser")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable @Argument Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/books")
    @QueryMapping(name = "booksByUserId")
    @Operation(summary = "Get BookDto list by userId")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<BookDto> getBooksByUserId(@PathVariable @Argument Long id) {
        List<Book> usersBooks = bookService.getAllByUserId(id);
        return bookMapper.toDto(usersBooks);
    }

    @PostMapping("/{id}/books")
    @MutationMapping(name = "createBook")
    @Operation(summary = "Add book to user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public BookDto createBook(
            @PathVariable(name = "id") @Argument Long userId,
            @Validated(OnCreate.class) @RequestBody @Argument BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book createdBook = bookService.create(book, userId);
        return bookMapper.toDto(createdBook);
    }
}
