package ru.romanov.booktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.book.BookImage;
import ru.romanov.booktracker.domain.exception.ResourceNotFoundException;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.repository.BookRepository;
import ru.romanov.booktracker.service.interfaces.BookService;
import ru.romanov.booktracker.service.interfaces.UserService;

import java.util.List;

import static ru.romanov.booktracker.domain.book.Status.PLANNED_TO_READ;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "BookService::findById", key = "#id")
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No book with id " + id));
    }

    // не кэшируем потому что сложно/дорого менять состояние кэша при добавлении/удалении книг у юзера
    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByUserId(Long userId) {
        return bookRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    @Cacheable(value = "BookService::findById", key = "#book.id")
    public Book create(Book book, Long userId) {
        User user = userService.findById(userId);
        book.setStatus(PLANNED_TO_READ);
        user.getBooks().add(book);
        userService.update(user);
        return book;
    }

    @Transactional
    @Override
    @CachePut(value = "BookService::findById", key = "#book.id")
    public Book update(Book book) {
        if (book.getStatus() == null) {
            book.setStatus(PLANNED_TO_READ);
        }
        bookRepository.save(book);
        return book;
    }

    @Transactional
    @Override
    @CacheEvict(value = "BookService::findById", key = "#id")
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void uploadImage(Long bookId, BookImage image) {
        Book book = findById(bookId);
        String fileName = imageService.uploadImage(image);
        book.getImages().add(fileName);
        bookRepository.save(book);
    }

    @Override
    public Book create(Book book) {
        return null;
    }
}
