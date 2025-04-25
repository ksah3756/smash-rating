package com.smashrating.auth.application;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.dto.UserPrincipal;
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDto userDto = UserDto.of(
                user.getRole().toString(),
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
        return UserPrincipal.create(userDto);
    }
}
