package no.soprasteria.bugster.infrastructure.db;

import no.soprasteria.bugster.infrastructure.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;

public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);
    private final static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();
    private static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    private final DataSource dataSource;

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public Database(String name) {
        try {
            this.dataSource = (DataSource) new InitialContext().lookup(name);
        } catch (NamingException e) {
            throw ExceptionUtil.soften(e);
        }
    }

    /**
     * Insert an object into the database. Used for create operations.
     *
     * @param query      in SQL stated as a prepared statement.
     * @param parameters values for the prepared statement.
     * @return the database field id from the inserted element.
     */
    public int insert(String query, Object... parameters) {
        return executeDbOperation(query, Arrays.asList(parameters), stmt -> {
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        });
    }

    /**
     * Retrieves a list of results from the database and maps it to an object.
     *
     * @param query      in SQL stated as a prepared statement.
     * @param mapper     definition for mapping fields to the returned objects in the
     *                   list.
     * @param parameters for the prepared statement.
     * @return database result mapped to list of classes.
     */
    public <T> List<T> queryForList(String query, RowMapper<T> mapper, Object... parameters) {
        return executeDbOperation(query, Arrays.asList(parameters), stmt -> {
            try (ResultSet rs = stmt.executeQuery()) {
                Row row = new Row(rs);
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapper.run(row));
                }
                return result;
            }
        });
    }

    /**
     * Retrieves a single result from the database and maps it to an objec.
     *
     * @param query      in SQL stated as a prepared statement.
     * @param parameters for the prepared statement.
     * @param mapper     definition for mapping fields to the returned object.
     * @return database result mapped to class.
     */
    private <T> Optional<T> queryForSingle(String query, Collection<Object> parameters, RowMapper<T> mapper) {
        return executeDbOperation(query, parameters, stmt -> {
            try (ResultSet rs = stmt.executeQuery()) {
                return mapSingleRow(rs, mapper);
            }
        });
    }

    public <T> Optional<T> queryForSingle(String query, String parameter, RowMapper<T> mapper) {
        return queryForSingle(query, Collections.singletonList(parameter), mapper);
    }

    public <T> Optional<T> queryForSingle(String query, long parameter, RowMapper<T> mapper) {
        return queryForSingle(query, Collections.singletonList(parameter), mapper);
    }

    /**
     * Update or delete operation sent to the database.
     *
     * @param query      in SQL stated as a prepared statement.
     * @param parameters for the prepared statement.
     */
    public void executeOperation(String query, Object... parameters) {
        executeDbOperation(query, Arrays.asList(parameters), PreparedStatement::executeUpdate);
    }

    /**
     * Create a transaction for multiple database operations like
     * {@link #insert(String, Object...) insert},
     * {@link #queryForList(String, RowMapper, Object...) queryForList},
     * {@link #executeOperation(String, Object...) executeOperation}
     *
     * @param operation is a functional interface to allow transaction to run in a
     *                  thread.
     */
    public void doInTransaction(Runnable operation) {
        try (Connection connection = dataSource.getConnection()) {
            threadConnection.set(connection);
            try {
                operation.run();
            } finally {
                threadConnection.set(null);
            }
        } catch (SQLException e) {
            throw handleException(e);
        }
    }

    private <T> T doWithConnection(ConnectionCallback<T> object) throws SQLException {
        if (threadConnection.get() != null) {
            return object.run(threadConnection.get());
        }

        try (Connection conn = dataSource.getConnection()) {
            return object.run(conn);
        }
    }

    private <T> T executeDbOperation(String query, Collection<Object> parameters,
                                     StatementCallback<T> statementCallback) {
        try {
            return doWithConnection(conn -> {
                log.info("Executing: {} with params {}", query, parameters);
                try (PreparedStatement prepareStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    int index = 1;
                    for (Object object : parameters) {
                        prepareStatement.setObject(index++, object);
                    }

                    return statementCallback.run(prepareStatement);
                }
            });
        } catch (SQLException e) {
            throw handleException(e);
        }
    }

    private RuntimeException handleException(SQLException e) {
        return ExceptionUtil.soften(e);
    }

    private <T> Optional<T> mapSingleRow(ResultSet rs, RowMapper<T> mapper) throws SQLException {
        if (!rs.next()) {
            return Optional.empty();
        }
        T result = mapper.run(new Row(rs));
        if (rs.next()) {
            throw new RuntimeException("Duplicate");
        }
        return Optional.of(result);
    }

    public interface RowMapper<T> {
        T run(Row row) throws SQLException;
    }

    private interface ConnectionCallback<T> {
        T run(Connection conn) throws SQLException;
    }

    private interface StatementCallback<T> {
        T run(PreparedStatement stmt) throws SQLException;
    }

    public static class Row {

        private final ResultSet rs;
        private final Map<String, Integer> columnMap = new HashMap<>();

        public Row(ResultSet rs) throws SQLException {
            this.rs = rs;
            for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                String tableName = rs.getMetaData().getTableName(i);
                String columnName = rs.getMetaData().getColumnName(i);

                this.columnMap.put(tableName + "." + columnName, i);
            }
        }

        public String getString(String string) throws SQLException {
            return rs.getString(string);
        }

        public int getInt(String columnName) throws SQLException {
            return rs.getInt(columnName);
        }

        public Instant getInstant(String columnName) throws SQLException {
            Timestamp timestamp = rs.getTimestamp(columnName);
            return timestamp != null ? timestamp.toInstant() : null;
        }

        public long getLong(String columnName) throws SQLException {
            return rs.getLong(columnName);
        }

        public String getString(String tableName, String columnName) throws SQLException {
            return rs.getString(getColumnIndex(tableName, columnName));
        }

        public boolean getBoolean(String tableName, String columnName) throws SQLException {
            return rs.getBoolean(getColumnIndex(tableName, columnName));
        }

        public double getDouble(String columnName) throws SQLException {
            return rs.getDouble(columnName);
        }

        private int getColumnIndex(String tableName, String columnName) {
            return columnMap.get(tableName + "." + columnName);
        }
    }
}
