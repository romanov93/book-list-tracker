package ru.romanov.booktracker.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.exception.ResourceMappingException;
import ru.romanov.booktracker.repository.DataSourceConfig;
import ru.romanov.booktracker.repository.interfaces.BookRepository;
import ru.romanov.booktracker.repository.mapper.BookRowMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.romanov.booktracker.repository.utils.TableColumnAliasStorage.*;

//@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT b.id as %s,
                   b.title as %s,
                   b.author as %s,
                   b.description as %s,
                   b.expiration_date_to_read as %s,
                   b.status as %s
            FROM books b
            WHERE id = ?
            """.formatted(
            BOOK_ID,
            BOOK_TITLE,
            BOOK_AUTHOR,
            BOOK_DESCRIPTION,
            BOOK_DATE_TO_READ,
            BOOK_STATUS
    );

    private final String FIND_ALL_BY_USER_ID = """
            SELECT b.id as %s,
                   b.title as %s,
                   b.author as %s,
                   b.description as %s,
                   b.expiration_date_to_read as %s,
                   b.status as %s
            FROM books b
            JOIN users_books ub on b.id = ub.book_id
            WHERE ub.user_id = ?
            """.formatted(
            BOOK_ID,
            BOOK_TITLE,
            BOOK_AUTHOR,
            BOOK_DESCRIPTION,
            BOOK_DATE_TO_READ,
            BOOK_STATUS
    );

    private final String ASSIGN = """
            INSERT INTO users_books (book_id, user_id)
            VALUES (?, ?)
            """;

    private final String DELETE = """
            DELETE FROM books
            WHERE id = ?
            """;

    private final String CREATE = """
            INSERT INTO books(title, author, description, status, expiration_date_to_read)
            VALUES (?, ?, ?, ?, ?)
            """;

    private final String UPDATE = """
            UPDATE books
            SET title = ?,
                author = ?,
                description = ?,
                status = ?,
                expiration_date_to_read = ?
            WHERE id = ?
            """;
    @Override
    public Optional<Book> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(BookRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding book by id");
        }
    }

    @Override
    public List<Book> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return BookRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding books by user_id");
        }
    }

    @Override
    public void assignToUserById(Long userId, Long bookId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, bookId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assign book to user by user_id");
        }
    }

    // В JDBC нельзя сетать null, поэтому мы долджны делать проверки
    @Override
    public void update(Book book) {
        // Смотрим в shema.sql какие поля могут быть налл. Это поля: description, expirationDateToRead
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            if (book.getDescription() == null) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else
                preparedStatement.setString(3, book.getDescription());
            preparedStatement.setString(4, book.getStatus().name());
            if (book.getExpirationDateToRead() == null) {
                preparedStatement.setNull(5, Types.TIMESTAMP);
            } else
                preparedStatement.setTimestamp(5, Timestamp.valueOf(book.getExpirationDateToRead()));
            preparedStatement.setLong(6, book.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while update book");
        }
    }

    // Cтейтмент возвращает нам айдишник, который мы сетаем в нашу созданную книгу.
    // Для этого, при создании стейтмента, передаем вторым аргументом RETURN_GENERATED_KEYS
    @Override
    public void create(Book book) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            if (book.getDescription() == null) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else {
                preparedStatement.setString(3, book.getDescription());
            }
            preparedStatement.setString(4, book.getStatus().name());
            LocalDateTime localDateTime = book.getExpirationDateToRead();
            if (localDateTime == null) {
                preparedStatement.setNull(5, Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
            }
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next(); // передвигаемся на первую строку
                book.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while create book");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while delete book");
        }
    }
}