package com.smashrating.auth.oauth2.service;

import com.smashrating.auth.dto.*;
import com.smashrating.user.domain.User;
import com.smashrating.user.domain.Role;
import com.smashrating.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2LoginUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = null;

        switch(registrationId) {
            case "google":
                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
                break;
            case "kakao":
                oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
                break;
            case "naver":
                oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
                break;
            default:
                throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        }

        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();

        Optional<User> optionalMember = userRepository.findByUsername(username);
        User user;
        if(optionalMember.isEmpty()) {
            user = registerNewUser(username, oAuth2UserInfo);
        } else {
            user = updateExistingUser(optionalMember, username, oAuth2UserInfo);
        }

        UserDto userDto = UserDto.of(
                Role.ROLE_USER.toString(),
                user.getId(),
                username,
                ""
        );

        return com.smashrating.auth.dto.UserPrincipal.create(userDto);
    }

    private User updateExistingUser(Optional<User> optionalMember, String username, OAuth2UserInfo oAuth2UserInfo) {
        User user = optionalMember.get();
        User updateUser = User.create(
                username,
                user.getPassword(),
                oAuth2UserInfo.getName(),
                user.getNickname(),
                oAuth2UserInfo.getEmail(),
                user.getRole()
        );
        user.update(updateUser);

        return userRepository.save(user);
    }

    private User registerNewUser(String username, OAuth2UserInfo oAuth2UserInfo) {
        User user = User.create(
                username,
                "",
                oAuth2UserInfo.getName(),
                "",
                oAuth2UserInfo.getEmail(),
                Role.ROLE_USER
        );

        return userRepository.save(user);
    }
}
