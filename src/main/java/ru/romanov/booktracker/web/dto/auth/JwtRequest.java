package ru.romanov.booktracker.web.dto.auth;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class JwtRequest {


    @NotNull(message = "Username must be not null")
    String username;

    @NotNull(message = "Password must be not null")
    String password;

}