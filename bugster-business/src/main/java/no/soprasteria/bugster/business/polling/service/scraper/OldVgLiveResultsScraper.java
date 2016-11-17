package no.soprasteria.bugster.business.polling.service.scraper;

import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.OldMatchStatus;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class OldVgLiveResultsScraper extends ResultsScraper {

    private static final String SCORE_CLASS_NAME = "score";
    private static final String PENALTY_CLASS_NAME = "penaltyResult";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OldVgLiveResultsScraper.class);

    public OldVgLiveResultsScraper(String url) {
        super(url);
    }

    @Override
    public List<Match> poll() {
        List<Match> matches = new ArrayList<>();
        log.info("Starter jobb for å hente resultater fra: " + this.getUrl());
        try {
            Document doc = Jsoup.connect(this.getUrl()).userAgent("Mozilla").get();
            for (OldMatchStatus matchStatus : OldMatchStatus.values()) {
                log.info("Henter kamper som har status " + matchStatus);
                Elements elementsByClass = doc.getElementsByClass(matchStatus.getCssClass());
                matches.addAll(mapToDomainObjects(elementsByClass, matchStatus));
            }

        } catch (IOException e) {
            log.error("Noe gikk galt.", e);
        }
        log.info("Ferdig med jobb for å hente resultater fra: " + this.getUrl());
        return matches;
    }

    private List<Match> mapToDomainObjects(Elements elementsByClass, OldMatchStatus status) {
        List<Match> matches = new ArrayList<>();
        for (Element elementsByClas : elementsByClass) {
            Element match = elementsByClas.children().first();
            try {
                Match footballMatch = getFootballMatch(match, status);
                matches.add(footballMatch);
                log.info(footballMatch.toString());
            } catch (Exception e) {
                // Handle error
                log.error("Feil i resultat. ", e);
                break;
            }
        }
        return matches;
    }

    private Match getFootballMatch(Element match, OldMatchStatus status) throws IllegalArgumentException {
        Score score = extractScore(match);
        Team homeTeam = extractHomeTeam(match);
        Team awayTeam = extractAwayTeam(match);
        return new FootballMatch(homeTeam, awayTeam, score, status);
    }

    private Team extractHomeTeam(Element element) {
        return new Team(((TextNode)element.childNode(1)).text());
    }

    private Team extractAwayTeam(Element element) {
        return new Team(((TextNode)element.childNode(element.childNodeSize()-1)).text());
    }

    private Score extractScore(Element match) {
        Elements scores = match.getElementsByClass(SCORE_CLASS_NAME);
        Element home = scores.get(0);
        Element away = scores.get(1);
        Elements penaltyScores = match.getElementsByClass(PENALTY_CLASS_NAME);
        Score score = new Score(parseInt(home.text()), parseInt(away.text()));
        // TODO: Check if extra time!
        // TODO: Handle extratime
        if(!penaltyScores.isEmpty()) {
            Element element = penaltyScores.get(0);
            Elements homeScore = element.getElementsByClass("hscore");
            Elements awayScore = element.getElementsByClass("ascore");
            score.setAwayPenalties(parseInt(awayScore.get(0).text()));
            score.setHomePenalties(parseInt(homeScore.get(0).text()));
        }
        return score;
    }
}
