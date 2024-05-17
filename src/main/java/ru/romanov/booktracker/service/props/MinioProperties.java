package ru.romanov.booktracker.service.props;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@Data
@ConfigurationProperties(prefix = "minio")
@FieldDefaults(level = PRIVATE)
public class MinioProperties {

    String bucket;

    String url;

    String accessKey;

    String secretKey;

}
