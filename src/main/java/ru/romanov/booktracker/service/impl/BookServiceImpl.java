package ru.romanov.booktracker.service.impl;

import org.springframework.stereotype.Service;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.service.interfaces.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public Book findById(Long id) {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Book> getAllByUserId(Long userId) {
        return null;
    }

    @Override
    public Book create(Book book, Long userId) {
        return null;
    }

    @Override
    public Book create(Book book) {
        return null;
    }
}
