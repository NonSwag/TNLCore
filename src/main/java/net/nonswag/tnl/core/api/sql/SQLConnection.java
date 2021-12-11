package net.nonswag.tnl.core.api.sql;

import net.nonswag.tnl.core.api.errors.TNLRuntimeException;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.object.Duplicable;
import net.nonswag.tnl.core.api.object.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnection implements AutoCloseable, Duplicable {

    @Nonnull
    private final String url;
    @Nonnull
    private final String database;
    @Nonnull
    private final String username;
    @Nonnull
    private final String password;
    @Nonnull
    private final String driver;
    @Nonnull
    private Connection connection;

    public SQLConnection(@Nonnull String url, @Nonnull String database, @Nonnull String username, @Nonnull String password, @Nonnull String driver) throws SQLException {
        this.url = url;
        this.database = database;
        this.username = username;
        this.password = password;
        this.driver = driver;
        try {
            Class.forName(driver);
            DriverManager.setLoginTimeout(3000);
            this.connection = newConnection();
        } catch (ClassNotFoundException e) {
            Logger.error.println("Driver not found <'" + driver + "'>");
            throw new SQLException("Cannot load driver", e);
        }
    }

    public void disconnect() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            Logger.error.println(e);
        }
    }

    @Nonnull
    public String getUrl() {
        return url;
    }

    @Nonnull
    public String getDatabase() {
        return database;
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    @Nonnull
    public String getDriver() {
        return driver;
    }

    @Nonnull
    public Connection getConnection() {
        return connection;
    }

    private void setConnection(@Nonnull Connection connection) {
        this.connection = connection;
    }

    public boolean isClosed() {
        try {
            return getConnection().isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    public final void createTable(@Nonnull String name, @Nonnull Parameter... parameters) {
        if (parameters.length == 0) throw new TNLRuntimeException("A table must have at least one row");
        else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < parameters.length; i++) {
                Pair<String, String> parameter = parameters[i];
                builder.append("`").append(parameter.getKey()).append("` ").append(parameter.getValue());
                if (i + 1 < parameters.length) builder.append(", ");
            }
            executeUpdate("CREATE TABLE IF NOT EXISTS `" + getDatabase() + "`.`" + name + "` (" + builder + ")");
        }
    }

    @Nullable
    public ResultSet executeQuery(@Nonnull String command) {
        try {
            if (reconnect()) {
                ResultSet result = getConnection().prepareStatement(command).executeQuery();
                if (result.next()) return result;
            } else Logger.error.println("failed to execute update: cannot reconnect");
        } catch (SQLException e) {
            if (!reconnect()) Logger.error.println(e);
        }
        return null;
    }

    public void executeUpdate(@Nonnull String command) {
        try {
            if (reconnect()) getConnection().prepareStatement(command).executeUpdate();
            else Logger.error.println("failed to execute update: cannot reconnect");
        } catch (SQLException e) {
            if (!reconnect()) Logger.error.println(e);
        }
    }

    public boolean reconnect() {
        if (isClosed()) {
            try {
                setConnection(newConnection());
            } catch (SQLException e) {
                Logger.error.println(e);
                return false;
            }
        }
        return true;
    }

    @Nonnull
    private Connection newConnection() throws SQLException {
        return DriverManager.getConnection(getUrl() + getDatabase(), getUsername(), getPassword());
    }

    @Override
    public void close() {
        disconnect();
    }

    @Nonnull
    @Override
    public SQLConnection duplicate() {
        try {
            return new SQLConnection(getUrl(), getDatabase(), getUsername(), getPassword(), getDriver());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
