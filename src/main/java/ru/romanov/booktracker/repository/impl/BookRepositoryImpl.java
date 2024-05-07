package ru.romanov.booktracker.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.repository.interfaces.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {


    @Override
    public List<Book> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public void assignToUserById(Long userId, Long bookId) {

    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void create(Book book) {

    }

    @Override
    public void delete(Long id) {

    }
}