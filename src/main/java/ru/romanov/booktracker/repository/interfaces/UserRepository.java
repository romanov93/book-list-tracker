package ru.romanov.booktracker.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;

import java.util.Optional;

@Mapper
public interface UserRepository extends CrudRepository<User> {

    Optional<User> findByUsername(String username);

    void insertUserRole(@Param("userId") Long userId,@Param("role") Role role);

    // нужно для проверки доступа юзера к книге
    boolean isBookOwner(@Param("userId") Long userId,@Param("bookId") Long bookId);
}