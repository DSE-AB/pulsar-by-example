package se.dse.pulsarbyexample.chatter.server;

import org.junit.Assert;
import org.junit.Test;
import se.dse.pulsarbyexample.chatter.api.ChatterException;
import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

import java.util.List;

public class TestChatterServer {

    @Test
    public void testLoginAndPostAMessage() throws ChatterException {

        ChatterPersistence l_mockChatterPersistence = new ChatterPersistenceInMemory();

        ChatterServer l_chatterServer = new ChatterServerImpl(l_mockChatterPersistence);

        String l_token = l_chatterServer.login("merlin");
        Assert.assertNotNull(l_token);

        l_chatterServer.chat(l_token, "Test message");


        List<ChatterMessage> l_chatterMessageList = l_chatterServer.getChatter(0, Integer.MAX_VALUE);
        Assert.assertEquals(1, l_chatterMessageList.size());
        Assert.assertEquals("Test message", l_chatterMessageList.get(0).getContent());
        Assert.assertEquals("merlin", l_chatterMessageList.get(0).getUserId());

    }

}
