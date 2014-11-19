
package se.dse.pulsarbyexample.chatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.module.configmanager.api.Config;

public class Activator extends PulsarActivator {
    private final static Logger logger = LoggerFactory.getLogger(Activator.class);
    private Config config;

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {
        config = lookupService(Config.class);
    }
}
            