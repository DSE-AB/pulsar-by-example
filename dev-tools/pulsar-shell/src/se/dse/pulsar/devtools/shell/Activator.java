
package se.dse.pulsar.devtools.shell;

import org.apache.felix.service.command.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.core.api.configurators.ServiceConfigurator;
import se.dse.pulsar.core.api.configurators.ServiceDescriptor;
import se.dse.pulsar.devtools.shell.api.DevCommandService;
import se.dse.pulsar.module.configmanager.api.Config;

import java.util.Dictionary;
import java.util.Map;

public class Activator extends PulsarActivator {
    private final static Logger logger = LoggerFactory.getLogger(Activator.class);
    private Config config;

    @Override
    public void init(final ModuleContextBuilder i_moduleContextBuilder) {
        config = lookupService(Config.class);

        i_moduleContextBuilder.publish(DevCommandService.class).usingClass(DevCommandServiceImpl.class).withConfigurator(new ServiceConfigurator() {
            @Override
            public ServiceDescriptor apply(ServiceDescriptor i_serviceDescriptor) {
                Dictionary<String, Object> l_desc = (Dictionary<String, Object>) i_serviceDescriptor.osgiServiceProperties;
                l_desc.put("A", "B");
                l_desc.put(CommandProcessor.COMMAND_SCOPE, "pulsar-dev");
                l_desc.put(CommandProcessor.COMMAND_FUNCTION, new String[] { "alfa", "beta" });
                return i_serviceDescriptor;
            }
        });

    }
}
            