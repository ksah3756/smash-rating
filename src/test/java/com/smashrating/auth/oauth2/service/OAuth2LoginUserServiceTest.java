//// New Test File: OAuth2LoginUserServiceTest.java
//package com.smashrating.auth.oauth2.service;
//
//import com.smashrating.auth.dto.OAuth2UserInfo;
//import com.smashrating.auth.dto.UserDto;
//import com.smashrating.user.domain.Role;
//import com.smashrating.user.domain.User;
//import com.smashrating.user.infrastructure.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//class OAuth2LoginUserServiceTest {
//
//    private UserRepository userRepository;
//    private OAuth2LoginUserService oAuth2LoginUserService;
//
//    @BeforeEach
//    void setUp() {
//        userRepository = mock(UserRepository.class);
//        oAuth2LoginUserService = new OAuth2LoginUserService(userRepository);
//    }
//
//    @Test
//    void loadUser_registersNewUser_whenUserDoesNotExist() {
//        OAuth2UserRequest userRequest = TestOAuth2UserRequest.of("google");
//        OAuth2User mockOAuth2User = new DefaultOAuth2User(
//                List.of(),
//                Map.of("sub", "123456", "name", "Sangho", "email", "test@example.com"),
//                "sub"
//        );
//
//        OAuth2LoginUserService service = spy(oAuth2LoginUserService);
//        doReturn(mockOAuth2User).when(service).loadUser(userRequest);
//        doReturn(new TestGoogleUserInfo(mockOAuth2User.getAttributes())).when(service).parseUserInfo(any(), eq("google"));
//
//        when(userRepository.findByUsername("google_123456")).thenReturn(Optional.empty());
//
//        service.loadUser(userRequest);
//
//        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userRepository).save(userCaptor.capture());
//
//        User savedUser = userCaptor.getValue();
//        assertThat(savedUser.getUsername()).isEqualTo("google_123456");
//        assertThat(savedUser.getName()).isEqualTo("Sangho");
//        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
//        assertThat(savedUser.getRole()).isEqualTo(Role.ROLE_USER);
//    }
//
//    // Helper classes
//    static class TestGoogleUserInfo extends OAuth2UserInfo {
//        public TestGoogleUserInfo(Map<String, Object> attributes) {
//            super(attributes);
//        }
//
//        @Override public String getProviderId() { return (String) attributes.get("sub"); }
//        @Override public String getProvider() { return "google"; }
//        @Override public String getName() { return (String) attributes.get("name"); }
//        @Override public String getEmail() { return (String) attributes.get("email"); }
//    }
//}
//
//// Helper for mocking OAuth2UserRequest
//class TestOAuth2UserRequest {
//    static OAuth2UserRequest of(String registrationId) {
//        var clientRegistration = mock(org.springframework.security.oauth2.client.registration.ClientRegistration.class);
//        when(clientRegistration.getRegistrationId()).thenReturn(registrationId);
//        return new OAuth2UserRequest(clientRegistration, null);
//    }
//}