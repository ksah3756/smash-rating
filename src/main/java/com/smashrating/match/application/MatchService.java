package com.smashrating.match.application;

import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.implement.PendingMatchReader;
import com.smashrating.user.domain.User;
import com.smashrating.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final PendingMatchReader pendingMatchReader;
    private final UserReader userReader;

    public List<PendingMatchResponse> getReceivedMatches(User user) {
        // 상대가 sendUser
        pendingMatchReader.getReceivedPendingMatch(user.getId())
                .stream().map(
                        PendingMatch -> {
                            User opponent = userReader.getUserById(PendingMatch.getSendUserId());

                            PendingMatchResponse.of(
                                    opponent.getId(),
                                    opponent.getUsername(),
                                    opponent.
                            )
                        }
                )
                .toList;
    }

    public List<PendingMatchResponse> getSentMatches(User user) {
        return pendingMatchReader.getSentPendingMatch(user.getId());
    }
}
