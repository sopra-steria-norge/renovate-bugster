package no.soprasteria.bugster.application.controller;

import io.restassured.path.json.JsonPath;
import no.soprasteria.bugster.business.team.domain.Team;
import no.soprasteria.bugster.infrastructure.db.Database;
import no.soprasteria.bugster.infrastructure.db.repository.TeamRepository;
import no.soprasteria.bugster.infrastructure.db.repository.TestDatasource;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamControllerTest extends JerseyTest {
    private static final DataSource dataSource = TestDatasource.get();
    private static final Database database = new Database(dataSource);
    private static final TeamRepository teamRepository = new TeamRepository(database);

    @Override
    protected Application configure() {
        migrateSchemas();
        return initApplication();
    }

    @Test
    public void lists_all_teams() {
        Team start = initTeam("IK Start");
        Team rosenborg = initTeam("Rosenborg");

        final String result = target("teams").request().get(String.class);

        JsonPath jsonPath = new JsonPath(result);
        assertThat(jsonPath.getLong("[0].id")).isEqualTo(start.getId());
        assertThat(jsonPath.getString("[0].name")).isEqualTo(start.getName());
        assertThat(jsonPath.getLong("[1].id")).isEqualTo(rosenborg.getId());
        assertThat(jsonPath.getString("[1].name")).isEqualTo(rosenborg.getName());
    }

    private Team initTeam(String name) {
        Team start = new Team(name);
        teamRepository.insert(start);
        return start;
    }


    @Test
    public void retrieves_team_by_name() throws Exception {

    }


    @Test
    public void resultsByName() throws Exception {

    }

    private Application initApplication() {
        final TeamController teamController = new TeamController(teamRepository);
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(teamController);
        return resourceConfig;
    }

    private void migrateSchemas() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:db/seed");
        flyway.migrate();
    }

}