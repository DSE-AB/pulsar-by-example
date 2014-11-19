package se.dse.pulsarbyexample.chatter.api;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public interface ChatterMessage {

    long getTimestamp();
    String getContent();
    String getId();
    String getUserId();

}
