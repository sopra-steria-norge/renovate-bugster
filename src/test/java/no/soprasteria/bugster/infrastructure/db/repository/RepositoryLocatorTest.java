package no.soprasteria.bugster.infrastructure.db.repository;

import no.soprasteria.bugster.application.server.ReloadableAppConfigFile;
import no.soprasteria.bugster.infrastructure.db.Database;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class RepositoryLocatorTest {
    @Before
    public void setUp() throws Exception {
        ReloadableAppConfigFile instance = (ReloadableAppConfigFile) ReloadableAppConfigFile.getInstance();
        Database mock = mock(Database.class);
        Whitebox.setInternalState(instance, "database", mock);
    }

    @Test
    public void matchRepository() throws Exception {
        Repository repository = RepositoryLocator.instantiate(MatchRepository.class);

        assertThat(repository).isInstanceOf(Repository.class);
        assertThat(repository).isInstanceOf(MatchRepository.class);
    }

    @Test
    public void userRepository() throws Exception {
        Repository repository = RepositoryLocator.instantiate(UserRepository.class);

        assertThat(repository).isInstanceOf(Repository.class);
        assertThat(repository).isInstanceOf(UserRepository.class);
    }

}