package com.smashrating.user.application;

import com.smashrating.common.exception.CustomException;
import com.smashrating.user.domain.User;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.implement.UserValidator;
import com.smashrating.user.implement.UserWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserWriter userWriter;
    private final UserValidator userValidator;

    public UserCreateResponse createMember(UserCreateRequest request) {
        if(userValidator.isUsernameDuplicate(request.username())) {
            throw new CustomException(UserErrorCode.USER_DUPLICATE);
        }

        User savedUser = userWriter.createUser(
                request.username(),
                request.password(),
                request.name(),
                request.email()
        );

        return UserCreateResponse.of(savedUser.getId());
    }


}
