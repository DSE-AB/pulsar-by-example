package se.dse.pulsarbyexample.chatter.server.api;

import se.dse.pulsarbyexample.chatter.api.ChatterMessage;

import java.util.List;

public interface ChatterPersistence {

    void storeChatter(ChatterMessage i_chatterMessage);

    List<ChatterMessage> loadChatter(long i_chatterTimeStamp, int i_maxSize);
}
