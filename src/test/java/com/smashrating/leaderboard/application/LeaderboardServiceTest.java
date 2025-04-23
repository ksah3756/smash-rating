package com.smashrating.leaderboard.application;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.leaderboard.dto.RankEntry;
import com.smashrating.leaderboard.dto.RankResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class LeaderboardServiceTest {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @Test
    @DisplayName("getMyRank - 유저 점수 및 랭킹 조회")
    void getMyRank() {
        UserDto userDto = UserDto.of("ROLE_USER", 1L, "testName", "testId");
        UserPrincipal user = UserPrincipal.create(userDto);

        given(leaderboardRepository.getScore(user.getUsername())).willReturn(1000.0);
        given(leaderboardRepository.getRank(user.getUsername())).willReturn(5L);

        RankResponse response = leaderboardService.getMyRank(user);

        assertThat(response.username()).isEqualTo(user.getUsername());
        assertThat(response.score()).isEqualTo(1000.0);
        assertThat(response.rank()).isEqualTo(5L);
    }

    @Test
    @DisplayName("getRankListByPage - 페이지 별 랭킹 반환")
    void getRankListByPage() {
        List<RankEntry> entries = List.of(
                new RankEntry("user1", 1500.0),
                new RankEntry("user2", 1400.0),
                new RankEntry("user3", 1300.0)
        );

        given(leaderboardRepository.getAllRankings(2, 3)).willReturn(entries);

        List<RankResponse> response = leaderboardService.getRankListByPage(2, 3);

        assertThat(response).hasSize(3);
        assertThat(response.get(0).rank()).isEqualTo(4); // (2 - 1) * 3 + 1 = 4
        assertThat(response.get(1).username()).isEqualTo("user2");
    }

    @Test
    @DisplayName("getRankList - 첫 번째 페이지 랭킹 반환")
    void getRankListByPageFirstPage() {
        // Given
        List<RankEntry> entries = List.of(
                new RankEntry("user1", 1500.0),
                new RankEntry("user2", 1400.0),
                new RankEntry("user3", 1300.0)
        );
        given(leaderboardRepository.getAllRankings(1, 3)).willReturn(entries);

        // When
        List<RankResponse> response = leaderboardService.getRankListByPage(1, 3);

        // Then
        assertThat(response).hasSize(3);
        assertThat(response.get(0).rank()).isEqualTo(1); // 첫 번째 페이지는 랭크 1부터 시작
        assertThat(response.get(1).rank()).isEqualTo(2);
        assertThat(response.get(2).rank()).isEqualTo(3);
    }

    @Test
    @DisplayName("getRankListByPage - 빈 결과 처리")
    void getRankListByPageEmptyResult() {
        // Given
        given(leaderboardRepository.getAllRankings(5, 3)).willReturn(List.of());

        // When
        List<RankResponse> response = leaderboardService.getRankListByPage(5, 3);

        // Then
        assertThat(response).isEmpty();
    }

    @Test
    @DisplayName("getRankListByPage - 요청된 크기보다 적은 결과")
    void getRankListByPageFewerThanRequestedItems() {
        // Given
        List<RankEntry> entries = List.of(
                new RankEntry("user1", 1500.0),
                new RankEntry("user2", 1400.0)
        ); // 3개 요청했지만 2개만 반환
        given(leaderboardRepository.getAllRankings(3, 3)).willReturn(entries);

        // When
        List<RankResponse> response = leaderboardService.getRankListByPage(3, 3);

        // Then
        assertThat(response).hasSize(2); // 요청된 3개가 아닌 실제 반환된 2개
        assertThat(response.get(0).rank()).isEqualTo(7); // (3-1)*3+1 = 7
        assertThat(response.get(1).rank()).isEqualTo(8);
    }

    @Test
    @DisplayName("updateScore - 점수 갱신 후 최신 점수 반환")
    void updateScore() {
        given(leaderboardRepository.getScore("testUser")).willReturn(1100.0);

        Double score = leaderboardService.updateScore("testUser", 1100.0);

        then(leaderboardRepository).should().updateScore("testUser", 1100.0);
        then(leaderboardRepository).should().getScore("testUser");
        assertThat(score).isEqualTo(1100.0);
    }
}
