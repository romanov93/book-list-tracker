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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.romanov.booktracker.config.TestConfig;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.repository.BookRepository;
import ru.romanov.booktracker.repository.UserRepository;
import ru.romanov.booktracker.service.interfaces.UserService;
import ru.romanov.booktracker.web.dto.auth.JwtRequest;
import ru.romanov.booktracker.web.dto.auth.JwtResponse;
import ru.romanov.booktracker.web.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthServiceImpl authService;

    @Test
    void login() {
        Long userId = 1L;
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        Mockito.when(jwtTokenProvider.createAccessToken(userId, username, roles))
                        .thenReturn(accessToken);
        Mockito.when(jwtTokenProvider.createRefreshToken(userId, username))
                        .thenReturn(refreshToken);
        Mockito.when(userService.getByUsername(username))
                .thenReturn(user);

        JwtResponse jwtResponse = authService.login(jwtRequest);
        Mockito.verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        Mockito.verify(userService).getByUsername(username);
        Mockito.verify(jwtTokenProvider).createAccessToken(userId, username, roles);
        Mockito.verify(jwtTokenProvider).createRefreshToken(userId, username);

        assertThat(username).isEqualTo(jwtResponse.getUsername());
        assertThat(userId).isEqualTo(jwtResponse.getId());
        assertThat(jwtResponse.getAccessToken()).isNotNull();
        assertThat(jwtResponse.getRefreshToken()).isNotNull();
    }

    @Test
    void loginWithWrongPassword() {
        String username = "username";
        String wrongPassword = "wrongPassword";
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(wrongPassword);

        Mockito.when(authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           jwtRequest.getUsername(),
                           jwtRequest.getPassword()
                   )
                )).thenThrow(BadCredentialsException.class);

        Assertions.assertThrows(AuthenticationException.class,
                () -> authService.login(jwtRequest));
    }

    @Test
    void refresh() {
        String newAccessToken = "accessToken";
        String refreshToken = "refreshToken";
        String newRefreshToken = "newRefreshToken";

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setRefreshToken(newRefreshToken);
        jwtResponse.setAccessToken(newAccessToken);

        Mockito.when(jwtTokenProvider.refreshUserTokens(refreshToken))
                .thenReturn(jwtResponse);

        JwtResponse testResponse = authService.refresh(refreshToken);
        Mockito.verify(jwtTokenProvider).refreshUserTokens(refreshToken);
        assertThat(testResponse).isEqualTo(jwtResponse);
    }
}
