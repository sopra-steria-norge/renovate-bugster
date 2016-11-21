package no.soprasteria.bugster.business.polling.service.scraper;

import com.google.gson.Gson;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.VgLiveApi;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.event.Event;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.participant.Participant;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.score.ParticipantScore;
import no.soprasteria.bugster.business.polling.service.scraper.vglive.score.Score;
import no.soprasteria.bugster.business.team.domain.Team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewVgLiveResultsScraper extends ResultsScraper {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NewVgLiveResultsScraper.class);
    private Gson gson = new Gson();

    public NewVgLiveResultsScraper(String url) {
        super(url);
    }

    @Override
    public List<Match> poll() {
        List<Match> matches = new ArrayList<>();
        VgLiveApi vgLiveApi = null;
        log.info("Starter jobb for å hente resultater fra: " + this.getUrl());
        try {
            URL url = new URL(this.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            vgLiveApi = gson.fromJson(br, VgLiveApi.class);

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(vgLiveApi != null) {
            matches = mapToDomainObjects(vgLiveApi);
        }

        log.info("Ferdig med jobb for å hente resultater fra: " + this.getUrl());
        return matches;
    }

    private List<Match> mapToDomainObjects(VgLiveApi apiResult) {
        List<Match> matches = new ArrayList<>();
        for (Event event : apiResult.getEvents()) {
            Match match = null;
            try {
                Team homeTeam = getTeamById(event.getParticipants()[0], apiResult.getParticipants());
                Team awayTeam = getTeamById(event.getParticipants()[1], apiResult.getParticipants());
                ParticipantScore homeTeamScore = getScoreByTeamId(event.getParticipants()[0], apiResult.getScores());
                ParticipantScore awayTeamScore = getScoreByTeamId(event.getParticipants()[1], apiResult.getScores());
                no.soprasteria.bugster.business.match.domain.Score score = new no.soprasteria.bugster.business.match.domain.Score(homeTeamScore.getOrdinaryTime(), awayTeamScore.getOrdinaryTime());
                score.setHomeExtraTime(homeTeamScore.getExtraTime());
                score.setAwayExtraTime(awayTeamScore.getExtraTime());
                score.setHomePenalties(homeTeamScore.getPenaltyShootout());
                score.setAwayPenalties(awayTeamScore.getPenaltyShootout());

                match = new FootballMatch(homeTeam, awayTeam, score, event.getStatus().getType(), event.getStartDate());
                matches.add(match);
                log.info(match.toString());
            } catch (Exception e) {
                // Handle error
                log.error("Feil i resultat. " + match, e);
                break;
            }
        }
        return matches;
    }

    private Team getTeamById(int id, List<Participant> participants) {
        Participant participant = participants.stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return new Team(participant.getName());
    }

    private ParticipantScore getScoreByTeamId(int id, List<Score> scores) {
        Score score = scores.stream()
                .filter(it -> it.getParticipantId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return score.getScore();
    }
}
