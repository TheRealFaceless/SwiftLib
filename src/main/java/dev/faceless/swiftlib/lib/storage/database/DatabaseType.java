package dev.faceless.swiftlib.lib.storage.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public enum DatabaseType {
    SQLITE() {
        @Override
        public void runQuery(Connection connection, String query) {
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            } catch (SQLException e) {throw new RuntimeException("Sql query error", e);}
        }
    };

    public abstract void runQuery(Connection connection, String query);
}

