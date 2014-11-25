
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

        i_moduleContextBuilder.publish(ChatterServer.class)
                .usingClass(ChatterServerImpl.class);

        if (config.getBoolean("enableLocalInMemoryPersistence")) {


            i_moduleContextBuilder.bindLocal(ChatterPersistence.class)
                    .usingClass(ChatterPersistenceInMemory.class);
        } else {

            i_moduleContextBuilder.consume(ChatterPersistence.class);
        }

    }
}
            