package no.soprasteria.bugster.application.controller;

import io.restassured.path.json.JsonPath;
import no.soprasteria.bugster.application.server.AppConfig;
import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.business.bet.domain.Odds;
import no.soprasteria.bugster.business.match.domain.*;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.OddsRepository;
import no.soprasteria.bugster.infrastructure.db.repository.RepositoryLocator;
import no.soprasteria.bugster.infrastructure.db.repository.TestDatasource;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

 /*
        Diskusjonspunkter:
        Hvordan teste - her kjøres en 'semi'-fullstack-test.
        Alternativer - full-integrasjon (kjøre opp komplett server)
        Unit-test (med /uten mocking)

        Fordeler/ulemper

        Ved ekstern-integrasjon - ønsker man å kjøre slike tester som en del av bygget? Ønsker man å ha de i det hele tatt?
        Kan man på en enkel måte sette opp test der eksterne avhengigheter ikke er i bruk, mens man likevel kjører en fullstack intern test?

        Hva gjør kode enkel å teste - hva gjør den vanskelig. Hvordan er det å refaktorere kode med tester som sjekker 'koden', i.e mock?

        Sikre at vi har noe kode som er fullstendig umulig å teste (gjerne med litt feil), der det er duste-tester og man må refaktorere.

        Transaksjoner i tester - sikre frikobling. Hvordan er dette her? Er det tilfeldig? (mem) Hva skjer når neste test kommmer med litt
        inserts?
     */


public class MatchControllerTest extends JerseyTest {
    private static final DataSource dataSource = TestDatasource.get();
    private static final Database database = new Database(dataSource);
    private static MatchRepository matchRepository;
    private static OddsRepository oddsRepository;

    @Override
    protected Application configure() {
        migrateSchemas();
        AppConfig instance = ReloadableAppConfigFile.getInstance();
        Whitebox.setInternalState(instance, "database", database);
        matchRepository = RepositoryLocator.instantiate(MatchRepository.class);
        oddsRepository = RepositoryLocator.instantiate(OddsRepository.class);
        return initApplication();
    }

    @Test
    public void lists_all_matches() throws Exception {
        Match firstMatch = new MatchTestData().build();
        Match secondMatch = new MatchTestData().build();
        matchRepository.insert(firstMatch);
        matchRepository.insert(secondMatch);

        Response response = target("matches").request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        JsonPath jsonPath = new JsonPath(response.readEntity(String.class));
        assertThatReponseMatchesAt(0, jsonPath, firstMatch);
        assertThatReponseMatchesAt(1, jsonPath, secondMatch);
    }

    @Test
    @Ignore("Jeje")
    public void match_by_id_includes_odds() throws Exception {
        Match match = new FootballMatch(
                new Team("Liverpool"),
                new Team("IK Start"),
                new Score(0, 3),
                "completed",
                LocalDateTime.now());

        matchRepository.insert(match);
        Odds hjemme = new Odds(match.getId(), Result.H, 1.12);
        Odds uavgjort = new Odds(match.getId(), Result.U, 1.50);
        Odds borte = new Odds(match.getId(), Result.H, 2.41);
        oddsRepository.insert(hjemme);
        oddsRepository.insert(uavgjort);
        oddsRepository.insert(borte);

        Response response = target("matches").request().get();

        assertThat(response.getStatus()).isEqualTo(200);

        JsonPath jsonPath = new JsonPath(response.readEntity(String.class));
        assertThatReponseMatchesAt(0, jsonPath, match);
    }

    private void assertThatReponseMatchesAt(int index, JsonPath jsonPath, Match match) {
        assertThat(jsonPath.getInt(path(index, "id"))).isEqualTo(match.getId());
        assertThat(jsonPath.getString(path(index, "homeTeam.name"))).isEqualTo(match.getHomeTeam().getName());
        assertThat(jsonPath.getString(path(index, "awayTeam.name"))).isEqualTo(match.getAwayTeam().getName());
        assertThat(jsonPath.getInt(path(index, "score.id"))).isEqualTo(match.getScore().getId());
        assertThat(jsonPath.getInt(path(index, "score.home"))).isEqualTo(match.getScore().getHome());
        assertThat(jsonPath.getInt(path(index, "score.away"))).isEqualTo(match.getScore().getAway());
    }

    private String path(int index, String path) {
        return "["+index+"]."+path;
    }

    private void migrateSchemas() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:db/seed");
        flyway.migrate();
    }

    private Application initApplication() {
        final MatchController matchController = new MatchController(matchRepository, oddsRepository);
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(matchController);
        return resourceConfig;
    }

}