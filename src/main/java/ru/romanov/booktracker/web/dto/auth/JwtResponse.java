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
            example = "eyJhbGciOiJkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODk.nobUtzooO8cBe7064")
    String accessToken;

    @Schema(description = "refresh jwt token",
            example = "eyJhbGciOiInR5cCI6IkpXVCJ9.eyJpc3MiOiJoRE9NQUlOLyIsIn"
                   + "N1YiI6ImF1dGgwfDEyaWRlbnRpZmllciIsImh0dHBzOi8vWU9VUl9")
    String refreshToken;
}
