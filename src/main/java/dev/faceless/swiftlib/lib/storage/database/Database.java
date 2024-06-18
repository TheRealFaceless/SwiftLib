package dev.faceless.swiftlib.lib.storage.database;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.faceless.swiftlib.SwiftLib;
import dev.faceless.swiftlib.lib.text.ConsoleLogger;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@SuppressWarnings("unused")
@CanIgnoreReturnValue()
public class Database {
    private final Connection connection;
    private final DatabaseType type;

    public Database(DatabaseType type, String url) {
        this.type = type;
        try {
            this.connection = connect(url);
            if(SwiftLib.isDebugMode()) ConsoleLogger.logInfo("Connection established by a database (Url: " + url + ")");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect(String url) throws SQLException {
        return switch (type) {
            case SQLITE -> DriverManager.getConnection(url);
        };
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }

    public static Database createDbFile(String path, DatabaseType type, boolean relativeFromPluginsFolder) throws IOException {
        File file = new File(getFilePath(checkName(type, path), relativeFromPluginsFolder));
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Failed to create directories for database file ");
        }
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Failed to create database file " + file.getName());
        }else if(SwiftLib.isDebugMode()) ConsoleLogger.logInfo("Created or loaded database file: " + file.getName());

        return new Database(type, getConnectionUrl(type, file.getAbsolutePath()));
    }

    private static String getFilePath(String path, boolean relativeFromPluginsFolder) {
        if (relativeFromPluginsFolder) return new File(SwiftLib.getPluginDataFolder(), path).getPath();
        else return path;
    }

    private static String checkName(DatabaseType type, String name) {
        return switch (type) {
            case SQLITE -> !name.endsWith(".db") ? name + ".db" : name;
        };
    }

    private static String getConnectionUrl(DatabaseType type, String filePath) {
        return switch (type) {
            case SQLITE -> "jdbc:sqlite:" + filePath;
        };
    }
}
