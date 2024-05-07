package ru.romanov.booktracker.web.dto.user;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class UserDto {


    Long id;


    String name;


    String username;


    String password;


    String passwordConfirmation;
}