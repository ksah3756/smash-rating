package com.smashrating.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserPrinciple implements OAuth2User, UserDetails {

    private UserDto userDto;
    private Map<String, Object> attributes;

    private UserPrinciple(UserDto userDto) {
        this.userDto = userDto;
    }

    private UserPrinciple(UserDto userDto, Map<String, Object> attributes) {
        this.userDto = userDto;
        this.attributes = attributes;
    }

    public static UserPrinciple create(UserDto userDto) {
        return new UserPrinciple(userDto);
    }

    public static UserPrinciple create(UserDto userDto, Map<String, Object> attributes) {
        return new UserPrinciple(userDto, attributes);
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

    @Override
    public String getName() {
        return userDto.name();
    }

    public String getUsername() {
        return userDto.username();
    }

    public String getRole() {
        return userDto.role();
    }
}
