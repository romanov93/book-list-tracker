package ru.romanov.booktracker.repository.interfaces;


import ru.romanov.booktracker.domain.book.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book>{

    List<Book> findAllByUserId(Long userId);

    void assignToUserById(Long userId, Long bookId);

}
