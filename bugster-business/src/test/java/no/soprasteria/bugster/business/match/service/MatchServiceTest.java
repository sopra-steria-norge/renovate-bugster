package no.soprasteria.bugster.business.match.service;

import no.soprasteria.bugster.business.match.domain.Match;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchServiceTest {

    @Test
    public void should_find_all_matches() throws Exception {
        List<Match> allMatches = new MatchService(repository).findAll();

        assertThat(!allMatches.isEmpty());
    }
}