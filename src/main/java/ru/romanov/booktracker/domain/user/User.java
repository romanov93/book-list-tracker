package ru.romanov.booktracker.domain.user;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.romanov.booktracker.domain.book.Book;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class User implements Serializable {

    Long id;

    String name;

    String username;

    String password;

    String passwordConfirmation;

    Set<Role> roles;

    List<Book> books;
}
