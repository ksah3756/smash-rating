package com.smashrating.match.presentation;

import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.auth.resolver.AuthUserPrincipal;
import com.smashrating.match.application.MatchService;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
@Validated
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/me/received")
    public ResponseEntity<List<PendingMatchResponse>> getReceivedMatches(@AuthUserPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(matchService.getReceivedMatches(userPrincipal));
    }

    @GetMapping("/me/sent")
    public ResponseEntity<List<PendingMatchResponse>> getSentMatches(@AuthUserPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(matchService.getSentMatches(userPrincipal));
    }

    @GetMapping("/me/history")
    public ResponseEntity<List<MatchResultResponse>> getMatchHistory(
            @AuthUserPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid MatchResultRequest matchResultRequest) {
        return ResponseEntity.ok(matchService.getMatchHistory(userPrincipal, matchResultRequest));
    }

//    @PostMapping("/me")



}
