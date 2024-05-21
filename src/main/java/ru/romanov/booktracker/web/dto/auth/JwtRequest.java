package ru.romanov.booktracker.web.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Schema(description = "Request for login")
public class JwtRequest {

    @Schema(description = "User email", example = "senior@gmail.com")
    @NotNull(message = "Username must be not null")
    String username;

    @Schema(description = "User password", example = "password1")
    @NotNull(message = "Password must be not null")
    String password;

}
