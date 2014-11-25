package se.dse.pulsarbyexample.chatter.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.module.databaseaccess.api.DatabaseAccessPool;
import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChatterPersistenceInDB implements ChatterPersistence {

    private final static Logger logger = LoggerFactory.getLogger(ChatterPersistenceInDB.class);

    private final DatabaseAccessPool databaseAccessPool;

    @Inject
    public ChatterPersistenceInDB(@Named(ChatterStoreActivator.DB_NAME) DatabaseAccessPool i_databaseAccessPool) {
        databaseAccessPool = i_databaseAccessPool;
    }

    @Override
    public void storeChatter(ChatterMessage i_chatterMessage) {
        logger.debug("[storeChatter] storing message in database: {}", i_chatterMessage);
        try {
            databaseAccessPool.execute("INSERT INTO chatter VALUES (?, ? ,? ,?)",
                    i_chatterMessage.getId(), i_chatterMessage.getTimestamp(),
                    i_chatterMessage.getUserId(), i_chatterMessage.getContent());
        } catch (SQLException e) {
            logger.warn("[storeChatter] {}", e);
        }
    }

    @Override
    public List<ChatterMessage> loadChatter(long i_chatterTimeStamp, int i_maxSize) {
        final List<ChatterMessage> l_result = new LinkedList<>();
        Object[][] l_resultSet = databaseAccessPool.select("SELECT * FROM chatter WHERE timestamp > ? ORDER BY timestamp DESC",
                i_chatterTimeStamp);

        if (l_resultSet != null) {
            for (Object[] row : l_resultSet) {
                l_result.add(new ChatterMessage((String) row[0], (long) row[1], (String) row[2], (String) row[3]));
            }
        }

        return l_result;
    }
}
