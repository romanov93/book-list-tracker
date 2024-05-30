package ru.romanov.booktracker.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.romanov.booktracker.repository.BookRepository;
import ru.romanov.booktracker.repository.UserRepository;
import ru.romanov.booktracker.service.impl.AuthServiceImpl;
import ru.romanov.booktracker.service.impl.BookServiceImpl;
import ru.romanov.booktracker.service.impl.ImageServiceImpl;
import ru.romanov.booktracker.service.impl.UserServiceImpl;
import ru.romanov.booktracker.service.interfaces.AuthService;
import ru.romanov.booktracker.service.interfaces.BookService;
import ru.romanov.booktracker.service.interfaces.ImageService;
import ru.romanov.booktracker.service.interfaces.UserService;
import ru.romanov.booktracker.service.props.JwtProperties;
import ru.romanov.booktracker.service.props.MinioProperties;
import ru.romanov.booktracker.web.security.JwtTokenProvider;
import ru.romanov.booktracker.web.security.JwtUserDetailsService;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthenticationManager authenticationManager;

    @Bean
    @Primary
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "cnRydGh0cnRlaGJiZWZ0bmhqdHlreXVra211eWptdGd5aGJlcmZndmd3cmR2Zw=="
        );
        return jwtProperties;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService(userService());
    }

    @Bean
    public MinioClient minioClient() {
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    public MinioProperties minioProperties() {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setBucket("images");
        return minioProperties;
    }

    @Bean
    @Primary
    public ImageService imageService() {
        return new ImageServiceImpl(minioClient(), minioProperties());
    }

    @Bean
    public JwtTokenProvider tokenProvider() {
        return new JwtTokenProvider(
                jwtProperties(),
                userDetailsService(),
                userService()
        );
    }

    @Bean
    @Primary
    public UserService userService() {
        return new UserServiceImpl(
                testPasswordEncoder(),
                userRepository);
    }

    @Bean
    @Primary
    public BookService bookService() {
        return new BookServiceImpl(
                bookRepository,
                imageService()
        );
    }

    @Bean
    @Primary
    public AuthService authService() {
        return new AuthServiceImpl(
                authenticationManager,
                userService(),
                tokenProvider()
        );
    }
}
