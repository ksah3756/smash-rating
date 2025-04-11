package com.smashrating.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class MemberPrinciple implements OAuth2User, UserDetails {

    private MemberDto memberDto;
    private Map<String, Object> attributes;

    private MemberPrinciple(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    private MemberPrinciple(MemberDto memberDto, Map<String, Object> attributes) {
        this.memberDto = memberDto;
        this.attributes = attributes;
    }

    public static MemberPrinciple create(MemberDto memberDto) {
        return new MemberPrinciple(memberDto);
    }

    public static MemberPrinciple create(MemberDto memberDto, Map<String, Object> attributes) {
        return new MemberPrinciple(memberDto, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new SimpleGrantedAuthority(memberDto.role()));

        return collection;
    }

    @Override
    public String getPassword() {
        return memberDto.password();
    }

    @Override
    public String getName() {
        return memberDto.name();
    }

    public String getUsername() {
        return memberDto.username();
    }

    public String getRole() {
        return memberDto.role();
    }
}
