
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

    @Override
    public void init(ModuleContextBuilder i_moduleContextBuilder) {
        config = lookupService(Config.class);

        i_moduleContextBuilder.consume(ChatterServer.class);

        i_moduleContextBuilder.bindLocal(PrintStream.class).usingInstance(System.out).named("ChatPrintStream");
        i_moduleContextBuilder.bindLocal(Long.class).usingInstance(1000).named("PollFrequencyMS");

    }

    @Override
    public void start(ModuleInjector i_injector) throws ModuleNotReadyException {
        pollingThread = i_injector.getInstance(ChatterPollingThread.class);
        pollingThread.start();
        chatterCommandLine = i_injector.getInstance(ChatterCommandLine.class);
    }

    @Override
    public void run() {
        // create buffered input stream from System.in, but protect it from being closed using the ConsoleInputStream
        consoleInputStream = new ConsoleInputStream(System.in);
        BufferedReader l_shellInputReader = new BufferedReader(new InputStreamReader(consoleInputStream));
        Shell l_shell = CustomShellFactory.createShell(l_shellInputReader, System.out, System.err, "chattershell", "", chatterCommandLine);
        try {
            l_shell.commandLoop();
        } catch (Throwable ignore) {
            logger.info("[run] shell exited");
        }
    }

    @Override
    public void stop(Thread[] i_runThreads) {
        if (consoleInputStream != null) {
            consoleInputStream.close();
        }
        if (pollingThread != null) {
            pollingThread.interrupt();
        }
    }
}
            