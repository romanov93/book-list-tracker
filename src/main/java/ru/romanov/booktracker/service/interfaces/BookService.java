package ru.romanov.booktracker.service.interfaces;

import ru.romanov.booktracker.domain.book.Book;

import java.util.List;

public interface BookService extends BaseService<Book> {

    List<Book> getAllByUserId(Long userId);


    Book create(Book book, Long userId);


}
