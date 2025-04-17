package com.smashrating.match.presentation;

import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.auth.resolver.AuthUserId;
import com.smashrating.match.facade.MatchFacade;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchCreateRequest;
import com.smashrating.match.dto.PendingMatchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@Validated
@RequiredArgsConstructor
public class MatchController {
    private final MatchFacade matchFacade;

    @GetMapping("/me/received")
    public ResponseEntity<List<PendingMatchResponse>> getReceivedMatches(@AuthUserId Long userId) {
        return ResponseEntity.ok(matchFacade.getReceivedMatches(userId));
    }

    @GetMapping("/me/sent")
    public ResponseEntity<List<PendingMatchResponse>> getSentMatches(@AuthUserId Long userId) {
        return ResponseEntity.ok(matchFacade.getSentMatches(userId));
    }

    @GetMapping("/me/history")
    public ResponseEntity<List<MatchResultResponse>> getMatchHistory(
            @AuthUserId Long userId,
            @RequestBody @Valid MatchResultRequest matchResultRequest) {
        return ResponseEntity.ok(matchFacade.getMatchHistory(userId, matchResultRequest));
    }

    @PostMapping("/request")
    public ResponseEntity<Void> createPendingMatch(
            @AuthUserId Long userId,
            @RequestBody @Valid PendingMatchCreateRequest matchRequest) {
        matchFacade.createPendingMatch(userId, matchRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{matchId}/accept")
    public ResponseEntity<Void> acceptMatch(
            @AuthUserId Long userId,
            @PathVariable Long matchId) {
        matchFacade.acceptMatch(userId, matchId);
        return ResponseEntity.ok().build();
    }





}
