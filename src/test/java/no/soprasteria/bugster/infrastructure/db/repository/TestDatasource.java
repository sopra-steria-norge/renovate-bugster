package no.soprasteria.bugster.infrastructure.db.repository;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestDatasource {
    public static DataSource get(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
        return dataSource;
    }
}
