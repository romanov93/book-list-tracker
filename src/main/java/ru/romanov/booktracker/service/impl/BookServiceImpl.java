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
import ru.romanov.booktracker.repository.BookRepository;
import ru.romanov.booktracker.service.interfaces.BookService;
import ru.romanov.booktracker.service.interfaces.ImageService;

import java.util.List;

import static ru.romanov.booktracker.domain.book.Status.PLANNED_TO_READ;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "BookService::findById", key = "#id")
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No book with id " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByUserId(Long userId) {
        return bookRepository.findAllByUserId(userId);
    }

    @Transactional
    @Override
    @Cacheable(value = "BookService::findById", key = "#book.id")
    public Book create(Book book, Long userId) {
        book.setStatus(PLANNED_TO_READ);
        bookRepository.save(book);
        bookRepository.assignBook(userId, book.getId());
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
    @CacheEvict(value = "BookService::findById", key = "#bookId")
    public void uploadImage(Long bookId, BookImage image) {
        String fileName = imageService.uploadImage(image);
        bookRepository.addImage(bookId, fileName);
    }

    @Override
    public Book create(Book book) {
        return null;
    }
}
