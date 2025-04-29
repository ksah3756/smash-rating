package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.enums.PendingMatchStatus;
import com.smashrating.rating.domain.Rating;
import com.smashrating.rating.exception.RatingErrorCode;
import com.smashrating.rating.exception.RatingException;
import com.smashrating.rating.infrastructure.RatingRepository;
import com.smashrating.user.domain.User;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.exception.UserException;
import com.smashrating.user.infrastructure.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// TODO: 실제 join처럼 수행하도록 변경
public class FakePendingMatchRepository implements PendingMatchRepository {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    private final List<PendingMatch> pendingMatches = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    public FakePendingMatchRepository(UserRepository userRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId) {
        return pendingMatches.stream()
                .filter(pendingMatch -> pendingMatch.getReceiveUserId().equals(receiveUserId))
                .filter(pendingMatch -> pendingMatch.getStatus().equals(PendingMatchStatus.PENDING))
                .map(pendingMatch -> createPendingMatchResponse(pendingMatch, pendingMatch.getSendUserId()))
                .toList();

    }

    @Override
    public List<PendingMatchResponse> getSentPendingMatch(Long sendUserId) {
        return pendingMatches.stream()
                .filter(pendingMatch -> pendingMatch.getSendUserId().equals(sendUserId))
                .filter(pendingMatch -> pendingMatch.getStatus().equals(PendingMatchStatus.PENDING))
                .map(pendingMatch -> createPendingMatchResponse(pendingMatch, pendingMatch.getReceiveUserId()))
                .toList();
    }

    private PendingMatchResponse createPendingMatchResponse(PendingMatch pendingMatch, Long opponentId) {
        User opponent = userRepository.findById(opponentId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Rating opponentRating = ratingRepository.findById(opponentId).orElseThrow(() -> new RatingException(RatingErrorCode.RATING_NOT_FOUND));
        return new PendingMatchResponse(
                pendingMatch.getId(),
                opponent.getUsername(),
                opponent.getNickname(),
                opponentRating.getScore(),
                opponentRating.getTier().toString()
        );
    }

    @Override
    public PendingMatch save(PendingMatch pendingMatch) {
        PendingMatch savedPendingMatch = PendingMatch.builder()
                .id(sequence.getAndIncrement())
                .sendUserId(pendingMatch.getSendUserId())
                .receiveUserId(pendingMatch.getReceiveUserId())
                .status(pendingMatch.getStatus())
                .build();

        pendingMatches.add(savedPendingMatch);

        return savedPendingMatch;
    }

    @Override
    public Optional<PendingMatch> findById(Long id) {
        return pendingMatches.stream()
                .filter(pendingMatch -> pendingMatch.getId().equals(id))
                .findFirst();
    }

}

