package ru.romanov.booktracker.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.romanov.booktracker.domain.book.Book;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "users")
@Data
@FieldDefaults(level = PRIVATE)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String username;

    String password;

    @Transient
    String passwordConfirmation;

    @Column(name = "role")
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    @CollectionTable(name = "users_roles")
    Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<Book> books;
}
