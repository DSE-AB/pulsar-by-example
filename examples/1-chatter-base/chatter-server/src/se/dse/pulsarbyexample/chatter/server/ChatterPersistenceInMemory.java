package se.dse.pulsarbyexample.chatter.server;

import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class ChatterPersistenceInMemory implements ChatterPersistence {

    private final LinkedList<ChatterMessage> chatterMessages = new LinkedList<>();

    private final Comparator<ChatterMessage> chatterComparator = new Comparator<ChatterMessage>() {
        @Override
        public int compare(ChatterMessage o1, ChatterMessage o2) {
            return (int)(o2.getTimestamp()-o1.getTimestamp());
        }
    };


    @Override
    public synchronized void storeChatter(ChatterMessage i_chatterMessage) {
        chatterMessages.addFirst(i_chatterMessage);
        Collections.sort(chatterMessages, chatterComparator); // keep the chatter sorted, newest first
    }

    @Override
    public synchronized List<ChatterMessage> loadChatter(long i_chatterTimeStamp, int i_maxSize) {

        int l_timestampIndex = getIndexFromTimeStamp(i_chatterTimeStamp);
        int l_resultIndex = Math.min(l_timestampIndex, i_maxSize);

        if (l_resultIndex > 0) {
            ChatterMessage[] l_result = new ChatterMessage[l_resultIndex];
            l_result = chatterMessages.subList(0, l_resultIndex).toArray(l_result);
            return Arrays.asList(l_result);
        } else {
            return Collections.emptyList();
        }
    }

    private int getIndexFromTimeStamp(long i_chatterTimeStamp) {
        if (chatterMessages.isEmpty() || chatterMessages.getFirst().getTimestamp() <= i_chatterTimeStamp) return 0;
        for (int i=0; i<chatterMessages.size(); i++) {
            if (chatterMessages.get(i).getTimestamp() <= i_chatterTimeStamp) return i;
        }
        return chatterMessages.size();

    }
}
