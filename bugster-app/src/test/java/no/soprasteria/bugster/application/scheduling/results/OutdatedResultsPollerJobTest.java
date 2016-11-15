package no.soprasteria.bugster.application.scheduling.results;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class OutdatedResultsPollerJobTest {

    OutdatedResultsPollerJob job = new OutdatedResultsPollerJob();

    @Test
    public void generateRandomNumberString() throws Exception {

        for(int i = 1; i < 100; i++) {
            String s = job.generateRandomNumberString(1, 12, 2);
            assertThat(s).hasSize(2);
            assertThat(Integer.parseInt(s)).isBetween(1, 12);
        }

    }

}