package com.andrej.springboot.user;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.andrej.springboot.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    USER(Set.of(CONTACT_READ)),
    ADMIN(Set.of(CONTACT_CREATE, CONTACT_READ, CONTACT_READ1, CONTACT_UPDATE, CONTACT_DELETE));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream().
                          map(permission -> new SimpleGrantedAuthority(permission.getPermission())).
                          collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
