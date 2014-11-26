
package se.dse.pulsarbyexample.chatter.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.module.configmanager.api.Config;
import se.dse.pulsar.module.databaseaccess.api.DatabaseAccess;
import se.dse.pulsar.module.databaseaccess.api.DatabaseAccessPool;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

import java.sql.SQLException;

public class ChatterStoreActivator extends PulsarActivator {

    private final static Logger logger = LoggerFactory.getLogger(ChatterStoreActivator.class);

    public final static String DB_NAME = "ChatterDB";
    public static final String NO_EXTERNAL_UPDATES_TABLE_STATUS_HANDLER = "se.dse.pulsar.module.databaseaccess.cache.handlers.NoExternalUpdateTableStatusHandler";

    private Config config;
    private DatabaseAccess databaseAccess;
    private DatabaseAccessPool databaseAccessPool;

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {
        config = lookupService(Config.class);

        i_moduleContextBuilder.consume(DatabaseAccessPool.class).named(DB_NAME);

        i_moduleContextBuilder.publish(ChatterPersistence.class).usingClass(ChatterPersistenceInDB.class)
                .named("DBPersistence");
    }

    @Override
    public void start(ModuleInjector i_injector) throws ModuleNotReadyException {
        databaseAccess = lookupService(DatabaseAccess.class);
        databaseAccessPool = setupDatabase();
    }

    @Override
    public void stop(Thread[] i_runThreads) {
        try {
            // since we are using an in memory we clean up the schema before stopping
            databaseAccessPool.execute("DROP TABLE chatter");
            databaseAccessPool.execute("DROP SCHEMA dbuser RESTRICT");
        } catch (SQLException e) {
            logger.warn("[stop] failed to drop schema: {}", e);
        }

        databaseAccess.deleteConnectionPool(DB_NAME);
    }

    private DatabaseAccessPool setupDatabase() {
        DatabaseAccessPool l_databaseAccessPool = databaseAccess.getDatabasePool(DB_NAME);
        if (l_databaseAccessPool == null) {
            try {

                l_databaseAccessPool = databaseAccess.createConnectionPool(
                        DB_NAME,
                        config.getString("dburl"),
                        config.getString("dbuser"),
                        config.getString("dbpassword"),
                        config.getString("driver_classname"),
                        config.getInteger("initial_connections"),
                        config.getInteger("max_connections"),
                        null,   // no extra properties for the JDBC driver
                        false,  // cache disable
                        NO_EXTERNAL_UPDATES_TABLE_STATUS_HANDLER, // exclusive use of db
                        100,            // cache objects limit, not used
                        1000 * 1000,    // cache size, not used
                        null    // no connection initialization statement
                );

                try {
                    l_databaseAccessPool.execute("CREATE SCHEMA dbuser");
                } catch (SQLException e) {
                    logger.warn("[setupDatabase] failed to create schema: {}", e);
                }

                try {
                    l_databaseAccessPool.execute("CREATE TABLE chatter (" +
                            "id         VARCHAR(100) PRIMARY KEY, " +
                            "timestamp  BIGINT," +
                            "userId     VARCHAR(80)," +
                            "content    VARCHAR(1000)" +
                            ")");
                } catch (SQLException e) {
                    logger.warn("[setupDatabase] failed to create table: {}", e);
                }

            } catch (Exception e) {
                logger.warn("[setupDatabase] {}", e);
            }

        }
        return l_databaseAccessPool;
    }

}
            