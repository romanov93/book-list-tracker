package ru.romanov.booktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.romanov.booktracker.domain.book.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = """
            SELECT * FROM books b
            JOIN users_books ub ON ub.book_id = b.id
            WHERE ub.user_id = :userId
            """, nativeQuery = true)
    List<Book> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
            INSERT INTO users_books (user_id, book_id)
            VALUES (:userId, :bookId)
            """, nativeQuery = true)
    void assignBook(
            @Param("userId") Long userId,
            @Param("bookId") Long bookId
    );

    @Modifying
    @Query(value = """
            INSERT INTO books_images (book_id, image)
            VALUES (:id, :fileName)
            """, nativeQuery = true)
    void addImage(
            @Param("id") Long id,
            @Param("fileName") String fileName
    );

}
