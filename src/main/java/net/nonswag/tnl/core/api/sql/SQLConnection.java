package net.nonswag.tnl.core.api.sql;

import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.object.Duplicable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

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

    @Nullable
    public ResultSet executeQuery(@Nonnull String query, @Nonnull Object... parameters) throws SQLException {
        if (reconnect()) {
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                for (int i = 0; i < parameters.length; i++) statement.setObject(i + 1, parameters[i]);
                CachedRowSet resultCached = RowSetProvider.newFactory().createCachedRowSet();
                resultCached.populate(statement.executeQuery());
                return resultCached;
            } catch (SQLException e) {
                if (!reconnect()) Logger.error.println("failed to reconnect to database", e);
                else throw e;
            }
        } else Logger.error.println("failed to execute query: cannot reconnect");
        return null;
    }

    public void executeUpdate(@Nonnull String query, @Nonnull Object... parameters) throws SQLException {
        try {
            if (reconnect()) {
                PreparedStatement statement = getConnection().prepareStatement(query);
                for (int i = 0; i < parameters.length; i++) statement.setObject(i + 1, parameters[i]);
                statement.executeUpdate();
            } else Logger.error.println("failed to execute update: cannot reconnect");
        } catch (SQLException e) {
            if (!reconnect()) Logger.error.println("failed to reconnect to database", e);
            else throw e;
        }
    }

    public boolean reconnect() {
        if (isClosed()) {
            try {
                setConnection(newConnection());
            } catch (SQLException e) {
                Logger.error.println("failed to reconnect to database", e);
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
        try {
            getConnection().close();
        } catch (SQLException e) {
            Logger.error.println(e);
        }
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
