package com.smashrating.user.facade;

import com.smashrating.common.exception.CustomException;
import com.smashrating.user.domain.User;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import com.smashrating.user.event.UserCreatedEvent;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.application.UserValidatorService;
import com.smashrating.user.application.command.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserCommandService userWriter;
    private final UserValidatorService userValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UserCreateResponse createMember(UserCreateRequest request) {
        checkUserInfoUniqueness(request);

        User savedUser = userWriter.createUser(
                request.username(),
                request.password(),
                request.realName(),
                request.nickname(),
                request.email()
        );

        eventPublisher.publishEvent(UserCreatedEvent.of(savedUser.getUsername()));
        return UserCreateResponse.of(savedUser.getId());
    }

    private void checkUserInfoUniqueness(UserCreateRequest request) {
        if(userValidator.isUsernameDuplicate(request.username())) {
            throw new CustomException(UserErrorCode.USER_USERNAME_DUPLICATE);
        }
        if(userValidator.isEmailDuplicate(request.email())) {
            throw new CustomException(UserErrorCode.USER_EMAIL_DUPLICATE);
        }
        if(userValidator.isNicknameDuplicate(request.nickname())) {
            throw new CustomException(UserErrorCode.USER_NAME_DUPLICATE);
        }
    }
}
