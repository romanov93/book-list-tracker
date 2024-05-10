package ru.romanov.booktracker.repository.interfaces;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.romanov.booktracker.domain.book.Book;

import java.util.List;

@Mapper
public interface BookRepository extends CrudRepository<Book>{

    List<Book> findAllByUserId(Long userId);

    void assignToUserById(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
