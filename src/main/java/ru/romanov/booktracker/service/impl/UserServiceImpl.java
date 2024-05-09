package ru.romanov.booktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.romanov.booktracker.domain.exception.ResourceNotFoundException;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.repository.interfaces.UserRepository;
import ru.romanov.booktracker.service.interfaces.UserService;

import java.util.Set;

import static ru.romanov.booktracker.domain.user.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));
    }

    @Transactional
    @Override
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User with this name is already exist.");
        } else if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password confirmation is not match to password.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        userRepository.insertUserRole(user.getId(), ROLE_USER);
        user.setRoles(Set.of(ROLE_USER));
        return user;
    }

    @Transactional
    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with username: " + username));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isBookOwner(Long userId, Long bookId) {
        return userRepository.isBookOwner(userId, bookId);
    }
}
