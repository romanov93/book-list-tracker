package ru.romanov.booktracker.repository.mapper;

import lombok.SneakyThrows;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.book.Status;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static ru.romanov.booktracker.repository.utils.TableColumnAliasStorage.*;

public class BookRowMapper {

    @SneakyThrows
    public static Book mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Book book = Book.builder()
                    .id(resultSet.getLong(BOOK_ID))
                    .author(resultSet.getString(BOOK_AUTHOR))
                    .title(resultSet.getString(BOOK_TITLE))
                    .description(resultSet.getString(BOOK_DESCRIPTION))
                    .status(Status.valueOf(resultSet.getString(BOOK_STATUS)))
                    .build();
            Timestamp timestamp = resultSet.getTimestamp(BOOK_DATE_TO_READ);
            // Перестраховываемся от NullPointerException
            if (timestamp != null)
                book.setExpirationDateToRead(timestamp.toLocalDateTime());

            return book;
        }
        return null;
    }

    @SneakyThrows
    public static List<Book> mapRows(ResultSet resultSet) {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong(BOOK_ID));
            // Не добавляем в лист книги с нуллами вместо полей
            if (!resultSet.wasNull()) {
                book.setAuthor(resultSet.getString(BOOK_AUTHOR));
                book.setTitle(resultSet.getString(BOOK_TITLE));
                String bookDescription = resultSet.getString(BOOK_DESCRIPTION);
                if (bookDescription != null)
                    book.setDescription(bookDescription);

                book.setStatus(Status.valueOf(resultSet.getString(BOOK_STATUS)));
                Timestamp timestamp = resultSet.getTimestamp(BOOK_DATE_TO_READ);
                // Перестраховываемся от NullPointerException
                if (timestamp != null)
                    book.setExpirationDateToRead(timestamp.toLocalDateTime());
                books.add(book);
            }
        }
        return books;
    }
}
