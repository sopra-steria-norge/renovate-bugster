package no.soprasteria.bugster.application.server;

import no.soprasteria.bugster.infrastructure.db.Database;

public interface AppConfig {

    int getHttpPort();

    Database getDatabase();

    void start();
}
