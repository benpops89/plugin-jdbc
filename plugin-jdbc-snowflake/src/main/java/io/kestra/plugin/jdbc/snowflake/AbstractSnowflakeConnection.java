package io.kestra.plugin.jdbc.snowflake;

import io.kestra.core.models.property.Property;
import io.kestra.core.models.tasks.Task;
import io.kestra.core.runners.RunContext;
import io.kestra.plugin.jdbc.JdbcConnectionInterface;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public abstract class AbstractSnowflakeConnection extends Task implements JdbcConnectionInterface, SnowflakeInterface {
    private Property<String> url;
    private Property<String> username;
    private Property<String> password;
    private Property<String> privateKey;
    private Property<String> privateKeyFile;
    private Property<String> privateKeyFilePassword;

    @Override
    public void registerDriver() throws SQLException {
        DriverManager.registerDriver(new net.snowflake.client.jdbc.SnowflakeDriver());
    }

    @Override
    public Properties connectionProperties(RunContext runContext) throws Exception {
        Properties properties = JdbcConnectionInterface.super.connectionProperties(runContext);

        this.renderProperties(runContext, properties);

        return properties;
    }
}
