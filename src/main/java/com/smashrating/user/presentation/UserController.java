package com.smashrating.user.presentation;

import com.smashrating.user.application.UserService;
import com.smashrating.user.dto.UserCreateRequest;
import com.smashrating.user.dto.UserCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class UserController {

    private final UserService memberService;

    @GetMapping("/my")
    public String myAPI() {
        return "Hello, this is my API!";
    }

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponse> register(
            @RequestBody @Valid UserCreateRequest request
    ) {
        return ResponseEntity.status(CREATED).body(memberService.createMember(request));
    }
}
