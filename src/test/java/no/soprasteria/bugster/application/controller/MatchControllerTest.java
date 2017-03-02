package no.soprasteria.bugster.application.controller;

import io.restassured.path.json.JsonPath;
import no.soprasteria.bugster.business.match.domain.FootballMatch;
import no.soprasteria.bugster.business.match.domain.Match;
import no.soprasteria.bugster.business.match.domain.Score;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import no.soprasteria.bugster.infrastructure.db.repository.MatchRepository;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;
import no.soprasteria.bugster.infrastructure.db.repository.TestDatasource;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class MatchControllerTest extends JerseyTest {
    private static final DataSource dataSource = TestDatasource.get();
    private static final Database database = new Database(dataSource);
    private static final TeamRepository teamRepository = new TeamRepository(database);
    MatchRepository matchRepository = new MatchRepository(database);

    @Override
    protected Application configure() {
        migrateSchemas();
        return initApplication();
    }

    @Test
    public void lists_all_matches() throws Exception {
        Match match = new FootballMatch(
                new Team("Liverpool"),
                new Team("IK Start"),
                new Score(0, 3),
                "completed",
                LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        matchRepository.insert(match);

        final String result = target("matches").request().get(String.class);
        JsonPath jsonPath = new JsonPath(result).get("[0]");

//        assertThat(jsonPath.getLong("[0].id")).isEqualTo(start.getId());
//        assertThat(jsonPath.getString("[0].name")).isEqualTo(start.getName());
//        assertThat(jsonPath.getLong("[1].id")).isEqualTo(rosenborg.getId());
//        assertThat(jsonPath.getString("[1].name")).isEqualTo(rosenborg.getName());
    }

    private void migrateSchemas() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:db/seed");
        flyway.migrate();
    }

    private Application initApplication() {
        final TeamController teamController = new TeamController(teamRepository);
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(teamController);
        return resourceConfig;
    }

}