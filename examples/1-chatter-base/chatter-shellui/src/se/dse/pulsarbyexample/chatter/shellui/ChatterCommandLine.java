package se.dse.pulsarbyexample.chatter.shellui;

import asg.cliche.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.ServiceUnavailableException;
import se.dse.pulsarbyexample.chatter.api.ChatterException;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;

import javax.inject.Inject;

public class ChatterCommandLine {

    private final static Logger logger = LoggerFactory.getLogger(ChatterCommandLine.class);

    private final ChatterServer chatterServer;
    private String chatterToken;

    @Inject
    public ChatterCommandLine(ChatterServer i_chatterServer) {
        chatterServer = i_chatterServer;
    }

    @Command
    public void login(String i_userName) {
        try {
            chatterToken = chatterServer.login(i_userName);
        } catch (ServiceUnavailableException e) {
            logger.warn("[login] Chatter Server in unavailable.");
        }
    }

    @Command
    public void say(String i_message) {
        try {
            chatterServer.chat(chatterToken, i_message);
        } catch (ServiceUnavailableException e) {
            logger.warn("[say] Chatter Server in unavailable.");
        } catch (ChatterException e) {
            logger.warn("[say] Exception when trying to send message: "+e.toString());
        }
    }

}
