
package se.dse.pulsarbyexample.chatter.shellui;

import asg.cliche.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.*;
import se.dse.pulsar.module.configmanager.api.Config;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;
import se.dse.pulsarbyexample.chatter.shellui.cliche.ConsoleInputStream;
import se.dse.pulsarbyexample.chatter.shellui.cliche.CustomShellFactory;

import java.io.*;

public class ChatterShellUIActivator extends PulsarActivator {
    private final static Logger logger = LoggerFactory.getLogger(ChatterShellUIActivator.class);
    private Config config;

    private Thread pollingThread;
    private ChatterCommandLine chatterCommandLine;
    private ConsoleInputStream consoleInputStream;

    protected final static String POLL_FREQUENCY_MS = "poll_frequency_ms";

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {
        config = lookupService(Config.class);

        i_moduleContextBuilder.consume(ChatterServer.class);

        // create some local dependency injection bindings to be used internally
        i_moduleContextBuilder.bindLocal(PrintStream.class).usingInstance(System.out).named("ChatPrintStream");

        try {
            long l_pollFrequencyMS = config.getLong(POLL_FREQUENCY_MS);
            i_moduleContextBuilder.bindLocal(Long.class).usingInstance(l_pollFrequencyMS).named(POLL_FREQUENCY_MS);
        } catch (NumberFormatException e) {
            logger.warn("[init] error getting the configuration parameter {}: {}", POLL_FREQUENCY_MS, e.toString());
        }

    }

    @Override
    public void start(ModuleInjector i_injector) throws ModuleNotReadyException {
        pollingThread = i_injector.getInstance(ChatterPollingThread.class);
        pollingThread.start();
        chatterCommandLine = i_injector.getInstance(ChatterCommandLine.class);
        // since start blocks other modules from starting we try to exit early and do
        // the rest of the setup asynchronous in the run() method
    }

    @Override
    public void run() {
        // create buffered input stream from System.in, but protect it from being closed using the ConsoleInputStream
        consoleInputStream = new ConsoleInputStream(System.in);
        BufferedReader l_shellInputReader = new BufferedReader(new InputStreamReader(consoleInputStream));
        // now use the Cliche library to show the command line prompt
        Shell l_shell = CustomShellFactory.createShell(l_shellInputReader, System.out, System.err, "chattershell", "", chatterCommandLine);
        try {
            l_shell.commandLoop();
        } catch (Throwable ignore) {
            logger.info("[run] shell exited");
        }
    }

    @Override
    public void stop(Thread[] i_runThreads) {
        // before we stop we need to close the input stream and also shut down our
        // thread used for message polling
        if (consoleInputStream != null) {
            consoleInputStream.close();
        }
        if (pollingThread != null) {
            pollingThread.interrupt();
        }
    }
}
            