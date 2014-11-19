package se.dse.pulsarbyexample.chatter.shellui.cliche;

import asg.cliche.*;
import asg.cliche.util.ArrayHashMultiMap;
import asg.cliche.util.MultiMap;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CustomShellFactory {

    public static Shell createShell(BufferedReader i_reader, PrintStream i_out, PrintStream i_err,
                                    String i_prompt, String i_appName, Object... i_handlers) {
        ConsoleIO l_consoleIO = new ConsoleIO(i_reader, i_out, i_err);

        List<String> l_path = new ArrayList<>(1);
        l_path.add(i_prompt);

        MultiMap<String, Object> i_modifAuxHandlers = new ArrayHashMultiMap<>();
        i_modifAuxHandlers.put("!", l_consoleIO);

        Shell l_shell = new Shell(new Shell.Settings(l_consoleIO, l_consoleIO, i_modifAuxHandlers, false),
                new CommandTable(new DashJoinedNamer(true)), l_path);
        l_shell.setAppName(i_appName);

        l_shell.addMainHandler(l_shell, "!");
        l_shell.addMainHandler(new HelpCommandHandler(), "?");
        for (Object h : i_handlers) {
            l_shell.addMainHandler(h, "");
        }

        return l_shell;
    }
}
