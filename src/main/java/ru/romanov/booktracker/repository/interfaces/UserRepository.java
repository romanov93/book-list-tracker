package ru.romanov.booktracker.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {

    Optional<User> findByUsername(String username);

    void insertUserRole(Long userId, Role role);

    // нужно для проверки доступа юзера к книге
    boolean isBookOwner(Long userId, Long bookId);
}