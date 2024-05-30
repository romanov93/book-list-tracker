package ru.romanov.booktracker.service.impl;

import ru.romanov.booktracker.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.book.BookImage;
import ru.romanov.booktracker.domain.book.Status;
import ru.romanov.booktracker.domain.exception.ResourceNotFoundException;
import ru.romanov.booktracker.repository.BookRepository;
import ru.romanov.booktracker.repository.UserRepository;
import ru.romanov.booktracker.service.interfaces.ImageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ImageService imageService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void getById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        Mockito.when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));

        Book testBook = bookService.getById(bookId);
        Mockito.verify(bookRepository, times(1)).findById(bookId);
        Assertions.assertEquals(book, testBook);
    }

    @Test
    void getByIdIfBookWithThisIdNotExisted() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        Mockito.when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> bookService.getById(bookId));
        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void getAllByUserId() {
        Long userId = 1L;
        List<Book> usersBooks = new ArrayList<>();
        for (int i = 0 ; i <= 5 ; i++) {
            usersBooks.add(new Book());
        }
        Mockito.when(bookRepository.findAllByUserId(userId))
                .thenReturn(usersBooks);

        List<Book> books = bookService.getAllByUserId(userId);
        Mockito.verify(bookRepository).findAllByUserId(userId);
        assertThat(usersBooks).isEqualTo(books);
    }

    @Test
    void update() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Victor Pelevin");
        book.setTitle("Generation P");
        book.setDescription("Story about young russian guy in advertising agency in 90s");
        book.setExpirationDateToRead(LocalDateTime.now());
        book.setStatus(Status.READ_DONE);

        Book testBook = bookService.update(book);
        Mockito.verify(bookRepository).save(book);
        assertThat(book).isEqualTo(testBook);
    }

    @Test
    void updateWithNullStatus() {
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("Victor Pelevin");
        book.setTitle("Generation P");
        book.setDescription("Story about young russian guy in advertising agency in 90s");
        book.setExpirationDateToRead(LocalDateTime.now());

        Book testBook = bookService.update(book);
        Mockito.verify(bookRepository).save(book);
        assertThat(testBook.getStatus()).isEqualTo(Status.PLANNED_TO_READ);
    }

    @Test
    void create() {
        Long bookId = 1L;
        Long userId = 1L;
        Book book = new Book();

        Mockito.doAnswer(invocationOnMock -> {
           Book savedBook = invocationOnMock.getArgument(0);
           savedBook.setId(bookId);
           return savedBook;
        }).when(bookRepository).save(book);

        Book createdBook = bookService.create(book, userId);
        Mockito.verify(bookRepository).save(book);
        assertThat(createdBook).isNotNull();
        Mockito.verify(bookRepository).assignBook(userId, bookId);
        assertThat(createdBook.getId()).isEqualTo(bookId);
    }

    @Test
    void delete() {
        Long bookId = 1L;
        bookService.delete(bookId);
        Mockito.verify(bookRepository).deleteById(bookId);
    }

    @Test
    void uploadImage() {
        Long bookId = 1L;
        String imageName = "imageName";
        BookImage bookImage = new BookImage();
        Mockito.when(imageService.uploadImage(bookImage))
                .thenReturn(imageName);
        bookService.uploadImage(bookId, bookImage);
        Mockito.verify(bookRepository).addImage(bookId, imageName);
    }

}
