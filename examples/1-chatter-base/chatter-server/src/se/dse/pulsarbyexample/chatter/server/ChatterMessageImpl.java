package se.dse.pulsarbyexample.chatter.server;

import se.dse.pulsarbyexample.chatter.api.ChatterMessage;

import java.util.UUID;

public class ChatterMessageImpl implements ChatterMessage {

    private final long timestamp;
    private final String content;
    private final String id;
    private final String userId;

    public ChatterMessageImpl(long i_timestamp, String i_content, String i_userId) {
        timestamp = i_timestamp;
        content = i_content;
        id = UUID.randomUUID().toString();
        userId = i_userId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserId() {
        return userId;
    }
}
