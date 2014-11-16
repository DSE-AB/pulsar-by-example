
package se.dse.pulsar.devtools.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.devtools.wiki.api.Wiki;
import se.dse.pulsar.module.clientaccess.api.configurators.RSConfiguratorFactory;
import se.dse.pulsar.module.configmanager.api.Config;
import se.dse.pulsar.module.modulemanager.api.ModuleManager;
import se.dse.pulsar.module.resourcemanager.api.ResourceManager;

import javax.inject.Inject;

public class WikiActivator extends PulsarActivator {
    private final static Logger logger = LoggerFactory.getLogger(WikiActivator.class);
    private Config config;

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {
        i_moduleContextBuilder.setModuleAlias("wiki");

        config = lookupService(Config.class);

        i_moduleContextBuilder
                .publish(Wiki.class)
                .usingClass(WikiImpl.class)
                .withConfigurator(lookupService(RSConfiguratorFactory.class).configure());

        i_moduleContextBuilder.extendUsing(WikiExtender.class).onResource("PULSAR-INF/resources/static/wiki");

        i_moduleContextBuilder.consume(ResourceManager.class);
        i_moduleContextBuilder.consume(ModuleManager.class);

    }

    @Inject
    WikiExtender wikiExtender;

    @Override
    public void start(ModuleInjector i_injector) throws ModuleNotReadyException {
        wikiExtender.extend(getModuleContext());
    }

    @Override
    public void stop(Thread[] i_runThreads) {
        wikiExtender.unExtend(getModuleContext());
    }
}
