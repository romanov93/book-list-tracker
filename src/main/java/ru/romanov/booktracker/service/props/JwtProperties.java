package ru.romanov.booktracker.service.props;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@Data
@ConfigurationProperties(prefix = "security.jwt")
@FieldDefaults(level = PRIVATE)
public class JwtProperties {

    String secret;

    long access;

    long refresh;

}
