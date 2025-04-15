package com.smashrating.match.presentation;

import com.smashrating.auth.resolver.AuthUser;
import com.smashrating.match.application.MatchService;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/match")
@Validated
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/me/received")
    public ResponseEntity<List<PendingMatchResponse>> getReceivedMatches(@AuthUser User user) {
        return ResponseEntity.ok(matchService.getReceivedMatches(user));
    }

    @GetMapping("/me/sent")
    public ResponseEntity<List<PendingMatchResponse>> getSentMatches(@AuthUser User user) {
        return ResponseEntity.ok(matchService.getSentMatches(user));
    }


}
