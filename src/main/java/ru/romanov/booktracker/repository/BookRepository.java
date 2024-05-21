package ru.romanov.booktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
