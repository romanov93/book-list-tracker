package ru.romanov.booktracker.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.romanov.booktracker.domain.book.Book;

import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class User {

    Long id;

    String name;

    String username;

    String password;

    String passwordConfirmation;

    Set<Role> roles;

    List<Book> books;
}
