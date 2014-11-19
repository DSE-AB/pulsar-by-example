package se.dse.pulsarbyexample.chatter.server;

import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class ChatterPersistenceInMemory implements ChatterPersistence {

    private final List<ChatterMessage> chatterMessages = new LinkedList<>();

    private final Comparator<ChatterMessage> chatterComparator = new Comparator<ChatterMessage>() {
        @Override
        public int compare(ChatterMessage o1, ChatterMessage o2) {
            return (int)(o1.getTimestamp()-o2.getTimestamp());
        }
    };


    @Override
    public synchronized void storeChatter(ChatterMessage i_chatterMessage) {
        chatterMessages.add(i_chatterMessage);
        Collections.sort(chatterMessages, chatterComparator); // keep the chatter sorted
    }

    @Override
    public synchronized List<ChatterMessage> loadChatter(long i_chatterTimeStamp, int i_maxSize) {

        int l_totalChatterSize = chatterMessages.size();
        int l_startChatterIndex = getNextChatterIndex(i_chatterTimeStamp);
        int l_resultSize = Math.min(l_totalChatterSize - l_startChatterIndex, i_maxSize);

        if (l_resultSize > 0) {
            ChatterMessage[] l_result = new ChatterMessage[l_resultSize];
            l_result = chatterMessages.subList(l_startChatterIndex, l_startChatterIndex + l_resultSize).toArray(l_result);
            return Arrays.asList(l_result);
        } else {
            return Collections.emptyList();
        }
    }

    private int getNextChatterIndex(long i_chatterTimeStamp) {
        for (int i=0; i<chatterMessages.size(); i++) {
            if (i_chatterTimeStamp < chatterMessages.get(i).getTimestamp()) {
                return i;
            }
        }
        return chatterMessages.size();
    }
}
