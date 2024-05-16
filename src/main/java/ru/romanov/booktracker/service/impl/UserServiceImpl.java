package ru.romanov.booktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(value = "UserService::getById", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id: " + id));
    }

    @Transactional
    @Override
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with username: " + username));
    }

    // если мы обновляем юзера - нам нужно, чтобы данные обновились не только в бд, но и в кэше
    @Transactional
    @Override
    @Caching(put = {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername", key = "#user.username")
    })
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    // если мы пытаемся создать юзера с уже существующим айди/юзернеймом - мы просто получаем кэш с тем, что уже есть
    @Transactional
    @Override
    @Caching(cacheable = {
            @Cacheable(value = "UserService::getById", key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername", key = "#user.username")
    })
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
    @Caching(evict = {
            @CacheEvict(value = "UserService::getById", key = "#id")
            // ToDo : очистить кэш метода findByUsername
    })
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    @Cacheable(value = "UserService::isBookOwner", key = "{#userId, #bookId}")
    public boolean isBookOwner(Long userId, Long bookId) {
        return userRepository.isBookOwner(userId, bookId);
    }
}