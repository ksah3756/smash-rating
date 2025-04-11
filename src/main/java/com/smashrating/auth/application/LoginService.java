package com.smashrating.auth.application;

import com.smashrating.auth.dto.MemberDto;
import com.smashrating.auth.dto.MemberPrinciple;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        MemberDto memberDto = MemberDto.of(
                user.getRole().toString(),
                user.getName(),
                user.getUsername(),
                user.getPassword()
        );
        return MemberPrinciple.create(memberDto);
    }
}
