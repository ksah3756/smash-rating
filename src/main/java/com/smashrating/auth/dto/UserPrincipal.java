package com.smashrating.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {

    private UserDto userDto;
    private Map<String, Object> attributes;

    private UserPrincipal(UserDto userDto) {
        this.userDto = userDto;
    }

    private UserPrincipal(UserDto userDto, Map<String, Object> attributes) {
        this.userDto = userDto;
        this.attributes = attributes;
    }

    public static UserPrincipal create(UserDto userDto) {
        return new UserPrincipal(userDto);
    }

    public static UserPrincipal create(UserDto userDto, Map<String, Object> attributes) {
        return new UserPrincipal(userDto, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(userDto.role()));

        return collection;
    }

    @Override
    public String getPassword() {
        return userDto.password();
    }

    public String getUsername() {
        return userDto.username();
    }

    public Long getId() {
        return userDto.id();
    }

    public String getRole() {
        return userDto.role();
    }

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

    @Override
    public String getName() {
        return userDto.username();
    }
}
