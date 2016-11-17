package no.soprasteria.bugster.business.polling.service;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.OldMatchStatus;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.polling.service.scraper.ResultsScraper;
import no.soprasteria.bugster.business.team.domain.Team;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResultServiceTest {

    private ResultService resultService = new ResultService();

    private ResultsScraper resultsScraperMock = mock(ResultsScraper.class);

    @Before
    public void setup() {
        resultService.setResultsScraper(resultsScraperMock);
    }

    @Test
    public void should_return_scheduled_matches() throws Exception {
        List<Match> allMatches = new ArrayList<>();
        allMatches.add(createMatch(OldMatchStatus.SCHEDULED));
        allMatches.add(createMatch(OldMatchStatus.FINISHED));
        when(resultsScraperMock.poll()).thenReturn(allMatches);

        assertThat(resultService.findByStatus(OldMatchStatus.SCHEDULED)).isNotEmpty();
    }

    @Test
    public void should_return_all_matches() throws Exception {
        List<Match> allMatches = new ArrayList<>();
        allMatches.add(createMatch(OldMatchStatus.SCHEDULED));
        allMatches.add(createMatch(OldMatchStatus.FINISHED));
        when(resultsScraperMock.poll()).thenReturn(allMatches);

        assertThat(resultService.findAll()).hasSize(2);
    }

    private Match createMatch(OldMatchStatus matchStatus) {
        return new FootballMatch(new Team("VIF"), new Team("LSK"), new Score(1, 1), matchStatus);
    }
}