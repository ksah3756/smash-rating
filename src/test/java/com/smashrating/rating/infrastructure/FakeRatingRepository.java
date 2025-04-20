package com.smashrating.rating.infrastructure;

import com.smashrating.match.domain.PendingMatchStub;
import com.smashrating.rating.domain.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeRatingRepository implements RatingRepository {
    private ConcurrentHashMap<Long, Rating> ratings = new ConcurrentHashMap<>();

    @Override
    public Rating save(Rating rating) {
        ratings.put(rating.getId(), rating);
        return rating;
    }

    @Override
    public Optional<Rating> findById(Long id) {
        return Optional.ofNullable(ratings.get(id));
    }

    @Override
    public void deleteById(Long id) {
        ratings.remove(id);
    }
}
