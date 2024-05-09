package ru.romanov.booktracker.repository.mapper;

import lombok.SneakyThrows;
import ru.romanov.booktracker.domain.book.Book;
import ru.romanov.booktracker.domain.book.Status;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookRowMapper {

    @SneakyThrows
    public static Book mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Book book = Book.builder()
                    .id(resultSet.getLong("book_id"))
                    .author(resultSet.getString("book_author"))
                    .title(resultSet.getString("book_title"))
                    .description(resultSet.getString("book_description"))
                    .status(Status.valueOf(resultSet.getString("book_read_status")))
                    .build();
            Timestamp timestamp = resultSet.getTimestamp("book_date_to_read");
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
            book.setId(resultSet.getLong("book_id"));
            // Не добавляем в лист книги с нуллами вместо полей
            if (!resultSet.wasNull()) {
                book.setAuthor(resultSet.getString("book_author"));
                book.setTitle(resultSet.getString("book_title"));
                String bookDescription = resultSet.getString("book_description");
                if (bookDescription != null)
                    book.setDescription(bookDescription);

                book.setStatus(Status.valueOf(resultSet.getString("book_read_status")));
                Timestamp timestamp = resultSet.getTimestamp("book_date_to_read");
                // Перестраховываемся от NullPointerException
                if (timestamp != null)
                    book.setExpirationDateToRead(timestamp.toLocalDateTime());
                books.add(book);
            }
        }
        return books;
    }
}
