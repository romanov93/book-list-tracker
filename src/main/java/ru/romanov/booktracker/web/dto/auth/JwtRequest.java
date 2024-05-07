package ru.romanov.booktracker.web.dto.auth;


import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class JwtRequest {


    String username;


    String password;

}