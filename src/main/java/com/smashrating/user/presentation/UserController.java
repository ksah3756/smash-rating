package com.smashrating.user.presentation;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.resolver.AuthUserDto;
import com.smashrating.user.dto.UserInfoResponse;
import com.smashrating.user.facade.UserFacade;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/my")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthUserDto UserDto userDto) {
        return ResponseEntity.ok(userFacade.getUserByUsername(userDto.username()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponse> register(
            @RequestBody @Valid UserCreateRequest request
    ) {
        return ResponseEntity.status(CREATED).body(userFacade.createUser(request));
    }
}
