package se.dse.pulsarbyexample.chatter.api;

import aQute.bnd.annotation.ProviderType;

import java.util.List;

@ProviderType
public interface ChatterServer {

    /**
     * Log in to the chat server.
     * @param i_username User name.
     * @return Login token, which is required to chat.
     */
    String login(String i_username);

    /**
     * Send a chat message
     * @param i_loginToken Login token, received by the login() method.
     * @param i_message
     * @see #login(String)
     */
    void chat(String i_loginToken, String i_message) throws ChatterException;

    /**
     * Retrieve a list of chat messages ordered according to timestamp, newest first.
     * @param i_sinceTimeStamp Only retrieve messages after this timestamp. Use 0 to get all messages.
     * @param i_maxCount Maximum number of messages to return
     * @return A list of messages.
     */
    List<ChatterMessage> getChatter(long i_sinceTimeStamp, int i_maxCount);

}
