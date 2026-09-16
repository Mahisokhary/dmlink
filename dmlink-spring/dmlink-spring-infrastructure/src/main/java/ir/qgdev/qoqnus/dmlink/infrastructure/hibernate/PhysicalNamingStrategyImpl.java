package ir.qgdev.qoqnus.dmlink.infrastructure.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Convert all names to snake case
 * And add prefix to the table names
 */
@RequiredArgsConstructor
public class PhysicalNamingStrategyImpl extends PhysicalNamingStrategySnakeCaseImpl {
    private final String prefix;

    private Identifier prefix(Identifier identifier) {
        if (identifier == null) return null;
        return Identifier.toIdentifier(prefix + "_" + identifier.getText());
    }

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return super.toPhysicalTableName(prefix(logicalName), jdbcEnvironment);
    }

}
