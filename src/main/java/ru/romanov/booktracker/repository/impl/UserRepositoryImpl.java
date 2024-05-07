package ru.romanov.booktracker.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.repository.interfaces.UserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void insertUserRole(Long userId, Role role) {

    }

    @Override
    public boolean isBookOwner(Long userId, Long bookId) {
        return false;
    }
}
