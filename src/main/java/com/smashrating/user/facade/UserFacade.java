package com.smashrating.user.facade;

import com.smashrating.user.application.query.UserQueryService;
import com.smashrating.user.domain.User;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import com.smashrating.user.dto.UserInfoResponse;
import com.smashrating.user.event.UserCreatedEvent;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.application.UserValidatorService;
import com.smashrating.user.application.command.UserCommandService;
import com.smashrating.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final UserValidatorService userValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UserCreateResponse createUser(UserCreateRequest request) {
        checkUserInfoUniqueness(request);

        User savedUser = userCommandService.createUser(
                request.username(),
                request.password(),
                request.realName(),
                request.nickname(),
                request.email()
        );

        eventPublisher.publishEvent(UserCreatedEvent.of(savedUser.getUsername()));
        return UserCreateResponse.of(savedUser.getId());
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserByUsername(String username) {
        User user = userQueryService.getUserByUsername(username);
        return UserInfoResponse.fromEntity(user);
    }

    private void checkUserInfoUniqueness(UserCreateRequest request) {
        if(userValidator.isUsernameDuplicate(request.username())) {
            throw new UserException(UserErrorCode.USER_USERNAME_DUPLICATE);
        }
        if(userValidator.isEmailDuplicate(request.email())) {
            throw new UserException(UserErrorCode.USER_EMAIL_DUPLICATE);
        }
        if(userValidator.isNicknameDuplicate(request.nickname())) {
            throw new UserException(UserErrorCode.USER_NICKNAME_DUPLICATE);
        }
    }
}
