package io.kestra.plugin.jdbc.clickhouse;

import io.kestra.core.models.property.Property;
import io.kestra.plugin.jdbc.AutoCommitInterface;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import io.kestra.core.models.annotations.Example;
import io.kestra.core.models.annotations.Plugin;
import io.kestra.core.models.tasks.RunnableTask;
import io.kestra.core.runners.RunContext;
import io.kestra.plugin.jdbc.AbstractCellConverter;
import io.kestra.plugin.jdbc.AbstractJdbcQuery;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;

@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Schema(
    title = "Query a Clickhouse database."
)
@Plugin(
    examples = {
        @Example(
            title = "Query a Clickhouse database.",
            full = true,
            code = """
                id: clickhouse_query
                namespace: company.team

                tasks:
                  - id: query
                    type: io.kestra.plugin.jdbc.clickhouse.Query
                    url: jdbc:clickhouse://127.0.0.1:56982/
                    username: ch_user
                    password: ch_password
                    sql: select * from clickhouse_types
                    fetchType: STORE
                """
        )
    }
)
public class Query extends AbstractJdbcQuery implements RunnableTask<AbstractJdbcQuery.Output>, AutoCommitInterface {
    protected final Property<Boolean> autoCommit = Property.of(true);

    @Override
    protected AbstractCellConverter getCellConverter(ZoneId zoneId) {
        return new ClickHouseCellConverter(zoneId);
    }

    @Override
    public void registerDriver() throws SQLException {
        DriverManager.registerDriver(new com.clickhouse.jdbc.ClickHouseDriver());
    }

    @Override
    public Output run(RunContext runContext) throws Exception {
        return super.run(runContext);
    }
}
