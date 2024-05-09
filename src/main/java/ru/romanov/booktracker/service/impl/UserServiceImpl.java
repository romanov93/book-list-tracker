package ru.romanov.booktracker.service.impl;

import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.service.interfaces.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public boolean isBookOwner(Long userId, Long bookId) {
        return false;
    }
}
