
package se.dse.pulsarbyexample.chatter.htmlui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.module.configmanager.api.Config;
import se.dse.pulsar.module.sessionmanager.api.SessionManager;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;
import se.dse.pulsarbyexample.chatter.htmlui.api.ChatterHtmlUI;

public class ChatterHtmlUIActivator extends PulsarActivator {
    private final static Logger logger = LoggerFactory.getLogger(ChatterHtmlUIActivator.class);
    private Config config;

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {

        config = lookupService(Config.class);

        i_moduleContextBuilder.consume(ChatterServer.class);
        i_moduleContextBuilder.consume(SessionManager.class);

        i_moduleContextBuilder.publish(ChatterHtmlUI.class).usingClass(ChatterHtmlUIImpl.class);

    }
}
            