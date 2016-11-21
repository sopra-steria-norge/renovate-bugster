package no.soprasteria.bugster.business.match.service;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MatchServiceTest {

    @Test
    public void should_find_all_matches() throws Exception {
        MatchRepository repository = mock(MatchRepository.class);
        List<Match> footballMatches = Arrays.asList(new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""), new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""), new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""), new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""));
        when(repository.list()).thenReturn(footballMatches);
        List<Match> allMatches = new MatchService(repository).findAll();

        assertThat(!allMatches.isEmpty());
    }

    @Test
    public void should_find_all_matchesWithLeeds() throws Exception {
        MatchRepository repository = mock(MatchRepository.class);
        List<Match> footballMatches = Arrays.asList(new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""), new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""), new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""), new FootballMatch(new Team("Leeds"), new Team("Leeds"), new Score(1, 1), "", ""));
        when(repository.findByName("Leeds")).thenReturn(footballMatches);
        List<Match> allMatches = new MatchService(repository).findAllByName("Leeds");

        assertThat(!allMatches.isEmpty());
    }
}