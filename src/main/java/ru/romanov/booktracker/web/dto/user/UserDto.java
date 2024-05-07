package ru.romanov.booktracker.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDto {


    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    Long id;

    @NotNull(message = "Name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name must be shorter then 256 symbols", groups = {OnCreate.class, OnUpdate.class})
    String name;

    @NotNull(message = "Username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username must be shorter then 256 symbols", groups = {OnCreate.class, OnUpdate.class})
    String username;

    @JsonProperty(access = WRITE_ONLY) // мы можем только принимать пароль, а отправлять сервер его не будет
    @NotNull(message = "Password must be not null", groups = {OnCreate.class, OnUpdate.class})
    String password;

    @JsonProperty(access = WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null", groups = {OnCreate.class})
    String passwordConfirmation;
}