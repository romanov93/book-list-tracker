package ru.romanov.booktracker.web.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class JwtEntity implements UserDetails {

    Long id;
    final String name;
    final String username;
    final String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
