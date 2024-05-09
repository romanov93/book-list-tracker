package ru.romanov.booktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.exception.ResourceNotFoundException;
import ru.romanov.booktracker.repository.interfaces.BookRepository;
import ru.romanov.booktracker.service.interfaces.BookService;

import java.util.List;

import static ru.romanov.booktracker.domain.book.Status.PLANNED_TO_READ;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No book with id " + id));
    }

    @Transactional
    @Override
    public Book create(Book book) {
        return null;
    }

    @Transactional
    @Override
    public Book create(Book book, Long userId) {
        // Создаем книгу, сетаем ей статус, связываем ее с юзером. Статус транзакшнл позволят все это сделать атомарно.
        book.setStatus(PLANNED_TO_READ);
        bookRepository.create(book);
        bookRepository.assignToUserById(userId, book.getId());
        return book;
    }

    @Transactional
    @Override
    public Book update(Book book) {
        if (book.getStatus() == null) {
            book.setStatus(PLANNED_TO_READ);
        }
        bookRepository.update(book);
        return book;
    }


    @Transactional
    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByUserId(Long userId) {
        return bookRepository.findAllByUserId(userId);
    }
}
