package com.smashrating.leaderboard;

import com.smashrating.auth.dto.UserPrinciple;
import com.smashrating.leaderboard.application.LeaderboardService;
import com.smashrating.leaderboard.dto.RankEntry;
import com.smashrating.leaderboard.dto.RankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @GetMapping("/my")
    public ResponseEntity<RankResponse> getMyRank(@AuthenticationPrincipal UserPrinciple user) {
        return ResponseEntity.ok().body(leaderboardService.getMyRank(user));
    }


    @GetMapping("/all")
    public ResponseEntity<RankResponse> getAllRank(
            @RequestParam (defaultValue = "0") int page
    ) {
        return ResponseEntity.ok().body(leaderboardService.getAllRank());
    }
}
