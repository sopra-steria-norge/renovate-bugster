package no.soprasteria.bugster.business.match.domain;

import no.soprasteria.bugster.business.team.domain.Team;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static no.soprasteria.bugster.business.match.domain.MatchStatus.FINISHED;
import static org.assertj.core.api.Assertions.assertThat;

public class FootballMatchTest {

    @Test
    public void should_sort_by_home_team_name_first_and_then_away_team_name() throws Exception {
        Team vif = new Team("VIF");
        Team rbk = new Team("RBK");
        Team til = new Team("TIL");
        Match match1 = new FootballMatch(vif, rbk, new Score(0, 0), FINISHED.getCssClass(), "");
        Match match2 = new FootballMatch(rbk, vif, new Score(0, 0), FINISHED.getCssClass(), "");
        Match match3 = new FootballMatch(til, vif, new Score(0, 0), FINISHED.getCssClass(), "");
        Match match4 = new FootballMatch(rbk, til, new Score(0, 0), FINISHED.getCssClass(), "");

        List<Match> matches = asList(match1, match2, match3, match4);
        Collections.sort(matches);
        assertThat(matches).containsExactly(match2, match4, match3, match1);

        Collections.reverse(matches);
        Collections.sort(matches);
        assertThat(matches).containsExactly(match4, match2, match3, match1);
    }
}