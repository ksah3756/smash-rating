package com.smashrating.rating.infrastructure;

import com.smashrating.rating.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {
    Rating save(Rating rating);

    Optional<Rating> findById(Long id);

    void deleteById(Long id);
}
