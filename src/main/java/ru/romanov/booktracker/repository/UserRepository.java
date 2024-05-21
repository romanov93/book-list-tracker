package ru.romanov.booktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.romanov.booktracker.domain.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // нужно для проверки доступа юзера к книге
    @Query(value = """
            SELECT exists(
                SELECT 1
                FROM users_books
                WHERE user_id = :userId
                AND book_id = :bookId)
            """, nativeQuery = true)
    boolean isBookOwner(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
