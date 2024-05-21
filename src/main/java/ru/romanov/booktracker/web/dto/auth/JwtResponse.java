package ru.romanov.booktracker.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Schema(description = "Login response")
public class JwtResponse {

    @Schema(description = "user id", example = "5")
    Long id;

    @Schema(description = "email", example = "torvalds@gmail.com")
    String username;

    @Schema(description = "authentication jwt token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkFsZX"
                   + "ggUGVyZWlyYSIsImlhdCI6MTUxNjIzOTAyMn0.nobUtzoKDph0A33laElWN8k5mDccaLOQYoO8cBe7064")
    String accessToken;

    @Schema(description = "refresh jwt token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL1lPVVJfRE9NQUlOLyIsIn"
                   + "N1YiI6ImF1dGgwfDEyMzQ1NiIsImF1ZCI6WyJteS1hcGktaWRlbnRpZmllciIsImh0dHBzOi8vWU9VUl9")
    String refreshToken;
}
