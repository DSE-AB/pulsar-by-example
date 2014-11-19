package se.dse.pulsarbyexample.chatter.shellui;

import se.dse.pulsar.core.api.ServiceUnavailableException;
import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.PrintStream;
import java.util.List;

public class ChatterPollingThread extends Thread {

    private final ChatterServer chatterServer;
    private final PrintStream printStream;
    private final long pollFrequencyMS;
    private long lastChatterTimeStamp;

    @Inject
    public ChatterPollingThread(ChatterServer i_chatterServer,
                                @Named("ChatPrintStream") PrintStream i_printStream,
                                @Named("PollFrequencyMS") long i_pollFrequencyMS) {
        chatterServer = i_chatterServer;
        printStream = i_printStream;
        pollFrequencyMS = i_pollFrequencyMS;
        lastChatterTimeStamp = System.currentTimeMillis(); // we don't want to see any old messages
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    List<ChatterMessage> l_newMessages = chatterServer.getChatter(lastChatterTimeStamp, Integer.MAX_VALUE);
                    if (!l_newMessages.isEmpty()) {
                        printChatter(l_newMessages);
                        lastChatterTimeStamp = l_newMessages.get(l_newMessages.size() - 1).getTimestamp();
                    }
                } catch (ServiceUnavailableException ignore) {}
                Thread.sleep(pollFrequencyMS);
            }
        } catch (InterruptedException ignore) {}
    }

    private void printChatter(List<ChatterMessage> i_chatterMessages) {
        for (ChatterMessage c : i_chatterMessages) {
            printStream.println("\n\t"+c.getUserId()+" > '"+c.getContent()+"'");
        }
    }
}


