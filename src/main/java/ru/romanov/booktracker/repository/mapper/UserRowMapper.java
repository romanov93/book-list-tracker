package ru.romanov.booktracker.repository.mapper;

import lombok.SneakyThrows;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {

    private static final String ROLES_PREFIX = "ROLE_";

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            String role = resultSet.getString("user_role");
            roles.add(Role.valueOf(role));
        }
        resultSet.beforeFirst(); // возвращаемся в начало RS благодаря TYPE_SCROLL_INSENSITIVE и CONCUR_READ_ONLY

        List<Book> books = BookRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();

        if (resultSet.next()) {
            return User.builder()
                    .id(resultSet.getLong("user_id"))
                    .name(resultSet.getString("user_name"))
                    .username(resultSet.getString("user_username"))
                    .password(resultSet.getString("user_password"))
                    .roles(roles)
                    .books(books)
                    .build();
        }
        return null;
    }
}
