package ru.romanov.booktracker.repository.mapper;

import lombok.SneakyThrows;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.romanov.booktracker.repository.utils.TableColumnAliasStorage.*;

public class UserRowMapper {

    private static final String ROLES_PREFIX = "ROLE_";

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            String role = resultSet.getString(USER_ROLE);
            roles.add(Role.valueOf(role));
        }
        resultSet.beforeFirst(); // возвращаемся в начало RS благодаря TYPE_SCROLL_INSENSITIVE и CONCUR_READ_ONLY

        List<Book> books = BookRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();

        if (resultSet.next()) {
            return User.builder()
                    .id(resultSet.getLong(USER_ID))
                    .name(resultSet.getString(USER_NAME))
                    .username(resultSet.getString(USER_USERNAME))
                    .password(resultSet.getString(USER_PASSWORD))
                    .roles(roles)
                    .books(books)
                    .build();
        }
        return null;
    }
}
