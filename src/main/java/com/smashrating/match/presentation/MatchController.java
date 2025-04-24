package com.smashrating.match.presentation;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.resolver.AuthUserDto;
import com.smashrating.match.facade.MatchFacade;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchCreateRequest;
import com.smashrating.match.dto.PendingMatchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@Validated
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MatchController {
    private final MatchFacade matchFacade;

    @GetMapping("/me/received")
    public ResponseEntity<List<PendingMatchResponse>> getReceivedMatches(@AuthUserDto UserDto userDto) {
        return ResponseEntity.ok(matchFacade.getReceivedMatches(userDto));
    }

    @GetMapping("/me/sent")
    public ResponseEntity<List<PendingMatchResponse>> getSentMatches(@AuthUserDto UserDto userDto) {
        return ResponseEntity.ok(matchFacade.getSentMatches(userDto));
    }

    @GetMapping("/me/history")
    public ResponseEntity<List<MatchResultResponse>> getMatchHistory(
            @AuthUserDto UserDto userDto,
            @ModelAttribute @Valid MatchResultRequest matchResultRequest) {
        return ResponseEntity.ok(matchFacade.getMatchHistory(userDto, matchResultRequest));
    }

    @PostMapping("/request")
    public ResponseEntity<Void> createPendingMatch(
            @AuthUserDto UserDto userDto,
            @RequestBody @Valid PendingMatchCreateRequest matchRequest) {
        matchFacade.createPendingMatch(userDto, matchRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{matchId}/accept")
    public ResponseEntity<Void> acceptMatch(@AuthUserDto UserDto userDto, @PathVariable Long matchId) {
        matchFacade.acceptMatch(userDto, matchId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{matchId}/reject")
    public ResponseEntity<Void> rejectMatch(@AuthUserDto UserDto userDto, @PathVariable Long matchId) {
        matchFacade.rejectMatch(userDto, matchId);
        return ResponseEntity.ok().build();
    }

    // TODO: 매치 결과 기입 API


}
