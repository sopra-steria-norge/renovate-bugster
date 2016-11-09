package no.soprasteria.bugster.application.server;

import no.soprasteria.bugster.application.scheduling.results.ResultPollerJob;
import no.soprasteria.bugster.infrastructure.config.AppConfigFile;
import no.soprasteria.bugster.infrastructure.db.Database;
import no.soprasteria.bugster.infrastructure.util.ExceptionUtil;
import no.soprasteria.bugster.infrastructure.util.IOUtil;
import org.eclipse.jetty.plus.jndi.EnvEntry;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

class EuroAppConfigFile extends AppConfigFile implements AppConfig {

    private Database database;

    EuroAppConfigFile() throws IOException {
        super(IOUtil.extractResourceFile("seedapp.properties"));
    }

    EuroAppConfigFile(Path configFile) {
        super(configFile);
    }

    protected DataSource createDataSource() {
        if (System.getenv("DATABASE_URL") != null) {
            return migrateDataSource("seed", createDataSourceFromEnv(System.getenv("DATABASE_URL")));
        }
        return createDataSource("seed");
    }

    @Override
    public int getHttpPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return Integer.parseInt(getProperty("seed.http.port", "8000"));
    }

    @Override
    public synchronized Database getDatabase() {
        if (database == null) {
            this.database = new Database("jdbc/seedappDs");
        }
        return database;
    }

    @Override
    public void start() {
        new File("logs").mkdirs();

        try {
            new EnvEntry("jdbc/seedappDs", createDataSource());
            new EnvEntry("seedapp/config", this);
        } catch (NamingException e) {
            throw ExceptionUtil.soften(e);
        }

        // Init scheduler
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = null;
        try {
            sched = schedFact.getScheduler();

            sched.start();
            JobDetail job = newJob(ResultPollerJob.class)
                    .withIdentity("resultPollerJob", "group1")
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("resultPollerJob", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(5)
                            .repeatForever())
                    .build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
