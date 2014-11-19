
package se.dse.pulsarbyexample.chatter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.module.configmanager.api.Config;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;
import se.dse.pulsarbyexample.chatter.server.api.ChatterPersistence;

public class ChatterServerActivator extends PulsarActivator {
    private final static Logger logger = LoggerFactory.getLogger(ChatterServerActivator.class);
    private Config config;

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {
        config = lookupService(Config.class);

        i_moduleContextBuilder.consume(ChatterPersistence.class);

        i_moduleContextBuilder.publish(ChatterServer.class)
                .usingClass(ChatterServerImpl.class);

        i_moduleContextBuilder.publish(ChatterPersistence.class)
                .usingClass(ChatterPersistenceInMemory.class)
                .named("InMemory");
    }
}
            