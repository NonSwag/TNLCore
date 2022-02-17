package net.nonswag.tnl.core.api.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.nonswag.tnl.core.api.object.Duplicable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class SQLConnection implements AutoCloseable, Duplicable {

    @Nonnull
    private final HikariDataSource dataSource;
    @Nullable
    private Connection connection;

    @Nonnull
    private final String url;
    @Nonnull
    private final String username;
    @Nonnull
    private final String password;
    @Nonnull
    private final String driver;

    public SQLConnection(@Nonnull String url, @Nonnull String username, @Nonnull String password, @Nonnull String driver) {
        HikariConfig config = new HikariConfig();
        config.setUsername(this.username = username);
        config.setPassword(this.password = password);
        config.setDriverClassName(this.driver = driver);
        config.setJdbcUrl(this.url = url);
        config.setConnectionTimeout(3000);
        config.setMaximumPoolSize(10);
        this.dataSource = new HikariDataSource(config);
    }

    @Nonnull
    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) connection = getDataSource().getConnection();
        return connection;
    }

    @Nullable
    public synchronized ResultSet executeQuery(@Nonnull String query, @Nonnull Object... parameters) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) statement.setObject(i + 1, parameters[i]);
            CachedRowSet resultCached = RowSetProvider.newFactory().createCachedRowSet();
            resultCached.populate(statement.executeQuery());
            return resultCached;
        }
    }

    public synchronized void executeUpdate(@Nonnull String query, @Nonnull Object... parameters) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) statement.setObject(i + 1, parameters[i]);
            statement.executeUpdate();
        }
    }

    @Override
    public synchronized void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException ignored) {
        } finally {
            getDataSource().close();
            connection = null;
        }
    }

    @Nonnull
    @Override
    public SQLConnection duplicate() {
        return new SQLConnection(getUrl(), getUsername(), getPassword(), getDriver());
    }
}
