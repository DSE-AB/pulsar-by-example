package se.dse.pulsarbyexample.chatter.htmlui.api;

import se.dse.pulsar.module.parammanager.api.PulsarMethod;
import se.dse.pulsar.module.util.api.XMLRepresentation;

public interface ChatterHtmlUI {

    @PulsarMethod
    void login(String i_userName);

    @PulsarMethod
    XMLRepresentation getChatter();

    @PulsarMethod
    void say(String i_message);


}
