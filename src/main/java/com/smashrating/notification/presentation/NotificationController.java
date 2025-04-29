package com.smashrating.notification.presentation;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.resolver.AuthUserDto;
import com.smashrating.notification.dto.FcmTokenRegisterRequest;
import com.smashrating.notification.enums.NotificationSenderType;
import com.smashrating.notification.facade.NotificationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationFacade notificationFacade;

    @PostMapping("/fcm/token")
    public ResponseEntity<Void> saveToken(@AuthUserDto UserDto userDto, @RequestBody FcmTokenRegisterRequest request) {
        notificationFacade.saveToken(NotificationSenderType.FCM, userDto.username(), request.token());
        return ResponseEntity.noContent().build();
    }

}
