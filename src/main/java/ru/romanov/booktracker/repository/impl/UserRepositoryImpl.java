package ru.romanov.booktracker.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.romanov.booktracker.domain.exception.ResourceMappingException;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.repository.DataSourceConfig;
import ru.romanov.booktracker.repository.interfaces.UserRepository;
import ru.romanov.booktracker.repository.mapper.UserRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role,
                   b.id as book_id,
                   b.author as book_author,
                   b.title as book_title,
                   b.description as book_description,
                   b.status as book_read_status,
                   b.expiration_date_to_read as book_date_to_read
            FROM users u
            LEFT JOIN users_roles ur ON u.id = ur.user_id
            LEFT JOIN users_books ub ON u.id = ub.user_id
            LEFT JOIN books b ON ub.book_id = b.id
            WHERE u.id = ?
            """;

    private final String FIND_BY_USERNAME = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role,
                   b.id as book_id,
                   b.author as book_author,
                   b.title as book_title,
                   b.description as book_description,
                   b.status as book_read_status,
                   b.expiration_date_to_read as book_date_to_read
            FROM users u
            LEFT JOIN users_roles ur ON u.id = ur.user_id
            LEFT JOIN users_books ub ON u.id = ub.user_id
            LEFT JOIN books b ON ub.book_id = b.id
            WHERE username = ?
            """;

    private final String UPDATE = """
            UPDATE users
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?   
            """;

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?
            """;

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles(user_id, role)
            VALUES (?, ?)
            """;

    private final String IS_BOOK_OWNER = """
            SELECT exists(
                SELECT 1
                FROM users_books
                WHERE user_id = ?
                AND book_id = ?
                )
            """;

    private final String CREATE = """
            INSERT INTO users(name, username, password)
            VALUES (?, ?, ?)
            """;


    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(FIND_BY_ID,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, // с помощью этих полей, мы сможем перемещаться по сету
                            ResultSet.CONCUR_READ_ONLY);       // и взад и вперед, получая сначала роли, а потом юзера
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while find user by id");
        }

    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, // с помощью этих полей, мы сможем перемещаться по сету
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                User user = UserRowMapper.mapRow(resultSet);
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while find user by username");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while update user");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while create new user");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while delete user");
        }

    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while insert role to user");
        }

    }

    @Override
    public boolean isBookOwner(Long userId, Long bookId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(IS_BOOK_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while check is user owns book or not");
        }
    }
}
