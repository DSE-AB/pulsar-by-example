package se.dse.pulsarbyexample.chatter.server;

import se.dse.pulsarbyexample.chatter.api.ChatterException;
import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatterServerImpl implements ChatterServer {

    private final Map<String, String> chatterSessions = new HashMap<>();

    private final ChatterPersistence chatterPersistence;

    @Inject
    public ChatterServerImpl(ChatterPersistence i_chatterPersistence) {
        chatterPersistence = i_chatterPersistence;
    }

    @Override
    public synchronized String login(String i_username) {
        if (chatterSessions.containsValue(i_username)) {
            return chatterSessions.get(i_username);
        } else {
            String l_chatterLoginToken = UUID.randomUUID().toString();
            chatterSessions.put(l_chatterLoginToken, i_username);
            return l_chatterLoginToken;
        }
    }

    @Override
    public synchronized void chat(String i_loginToken, String i_message) throws ChatterException {
        if (chatterSessions.containsKey(i_loginToken)) {

            ChatterMessage l_chatterMessage = new ChatterMessageImpl(
                    System.currentTimeMillis(),
                    i_message,
                    chatterSessions.get(i_loginToken));

            chatterPersistence.storeChatter(l_chatterMessage);
        } else {
            throw new ChatterException("Invalid chatter token");
        }
    }

    @Override
    public List<ChatterMessage> getChatter(long i_sinceTimeStamp, int i_maxCount) {
        return chatterPersistence.loadChatter(i_sinceTimeStamp, i_maxCount);
    }
}
