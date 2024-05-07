package ru.romanov.booktracker.service.interfaces;

import ru.romanov.booktracker.web.dto.auth.JwtRequest;
import ru.romanov.booktracker.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
