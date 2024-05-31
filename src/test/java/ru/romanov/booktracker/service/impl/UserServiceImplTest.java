package ru.romanov.booktracker.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanov.booktracker.config.TestConfig;
import ru.romanov.booktracker.domain.exception.ResourceNotFoundException;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.repository.BookRepository;
import ru.romanov.booktracker.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void getById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        User testUser = userService.getById(userId);
        Mockito.verify(userRepository).findById(userId);
        assertThat(user).isEqualTo(testUser);

    }

    @Test
    void getByIdIfUserWithThisIdIsNotExist() {
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(userId));
        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void getByUserName() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        User testUser = userService.getByUsername(username);
        Mockito.verify(userRepository).findByUsername(username);
        assertThat(user).isEqualTo(testUser);
    }

    @Test
    void getByUsernameIfUserWithThisUsernameIsNotExist() {
        String username = "username";
        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUsername(username));
        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void update() {
        String password = "password";
        User user = new User();
        user.setPassword(password);
        Mockito.when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        userService.update(user);
        Mockito.verify(passwordEncoder).encode(password);
        Mockito.verify(userRepository).save(user);
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void isTaskOwner() {
        Long userId = 1L;
        Long bookId = 1L;
        Mockito.when(userRepository.isBookOwner(userId, bookId))
                .thenReturn(true);
        boolean isOwner = userService.isBookOwner(userId, bookId);
        Mockito.verify(userRepository).isBookOwner(userId, bookId);
        assertThat(isOwner).isTrue();
    }

    @Test
    void create() {
        String username = "username";
        String password = "password";
        String encodedPassword = "encodedPassword";

        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setName("username");
        user.setPassword(password);
        user.setPasswordConfirmation(password);

        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password))
                .thenReturn(encodedPassword);

        User testUser = userService.create(user);
        Mockito.verify(userRepository).findByUsername(username);
        Mockito.verify(userRepository).save(user);

        assertThat(testUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(testUser.getRoles()).isEqualTo(Set.of(Role.ROLE_USER));
        assertThat(testUser.getUsername()).isEqualTo(username);
    }

    @Test
    void createIfPasswordNotMatchesWithConfirmation() {
        String username = "username";
        String password = "password";
        String passwordConfirmation = "pasworrd";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirmation(passwordConfirmation);

        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class,
                () -> userService.create(user));

        Mockito.verify(userRepository).findByUsername(username);
    }

    @Test
    void createIfUserAlreadyExist() {
        String username = "username";
        User user = new User();
        user.setUsername(username);

        Mockito.when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        Assertions.assertThrows(IllegalStateException.class,
                () -> userService.create(user));
    }

    @Test
    void delete() {
        Long userId = 1L;
        userService.delete(userId);
        Mockito.verify(userRepository).deleteById(userId);
    }
}
