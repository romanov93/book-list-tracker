package ru.romanov.booktracker.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.romanov.booktracker.domain.user.Role;
import ru.romanov.booktracker.domain.user.User;

import java.util.Collection;
import java.util.List;

@Component
public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                mapRolesToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<? extends GrantedAuthority> mapRolesToGrantedAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
}