package ru.romanov.booktracker.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import ru.romanov.booktracker.web.dto.validation.OnCreate;
import ru.romanov.booktracker.web.dto.validation.OnUpdate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User id",
            example = "1")
    @NotNull(message = "Id must be not null",
            groups = OnUpdate.class)
    Long id;

    @Schema(description = "User name",
            example = "Linus Torvalds")
    @NotNull(message = "Name must be not null",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255,
            message = "Name must be shorter then 256 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    String name;

    @Schema(description = "User email",
            example = "torvalds@gmail.com")
    @NotNull(message = "Username must be not null",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255,
            message = "Username must be shorter then 256 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    String username;

    @Schema(description = "User crypted password",
            example = "$2a$12$qAp2kySTgTH12uSEYfYruO55.iWLFODFBq1/bzkpA/QCBxgFrZvd6")
    @JsonProperty(access = WRITE_ONLY)
    @NotNull(message = "Password must be not null",
            groups = {OnCreate.class, OnUpdate.class})
    String password;

    @Schema(description = "User password confirmation",
            example = "$2a$12$qAp2kySTgTH12uSEYfYruO55.iWLFODFBq1/bzkpA/QCBxgFrZvd6")
    @JsonProperty(access = WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null",
            groups = {OnCreate.class})
    String passwordConfirmation;
}
