package ru.romanov.booktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.service.interfaces.AuthService;
import ru.romanov.booktracker.service.interfaces.UserService;
import ru.romanov.booktracker.web.dto.auth.JwtRequest;
import ru.romanov.booktracker.web.dto.auth.JwtResponse;
import ru.romanov.booktracker.web.security.JwtTokenProvider;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        /* Здесь менеджер аутентификации вызывает
         * наш UserDetailsService, достает юзера,
         * и проверяет на совпадение переданый пароль
         */
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));
        // Если аутентификация прошла - идем дальше:
        User user = userService.getByUsername(loginRequest.getUsername());
        return JwtResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .accessToken(jwtTokenProvider.createAccessToken(
                        user.getId(), user.getUsername(), user.getRoles()
                ))
                .refreshToken(jwtTokenProvider.createRefreshToken(
                        user.getId(), user.getUsername()
                ))
                .build();
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
