package se.dse.pulsar.devtools.wiki.plugins;

import org.markdown4j.CodeBlockEmitter;
import org.markdown4j.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PrettifyPlugin extends Plugin {

    private final File rootDir;

    public PrettifyPlugin(File i_rootDir) {
        super("prettify");
        rootDir = i_rootDir;
    }

    @Override
    public void emit(StringBuilder out, List<String> lines, Map<String, String> params) {
        if (getParam(params, "install", null) != null) {
            out.append("<script src=\"/pulsar/wiki/html/google-code-prettify/run_prettify.js\"></script>");
        } else {
            String l_filePath = getParam(params, "file", null);
            String l_title = getParam(params, "title", null);
            String l_lang = getParam(params, "lang", "text");
            String l_mark = getParam(params, "mark", null);
            boolean l_linenums = "true".equalsIgnoreCase(getParam(params, "linenums", null));

            String l_startMark = "<:"+l_mark;
            String l_endMark = l_mark+":>";

            if (l_filePath != null) {
                File l_sourceFile = new File(rootDir, l_filePath);
                boolean active = (l_mark == null);
                boolean headerOutputted = false;
                try (BufferedReader l_reader = new BufferedReader(new FileReader(l_sourceFile))) {

                    String s;
                    int line = 0;
                    while ((s = l_reader.readLine()) != null)
                    {
                        line++;
                        if (l_mark != null && s.trim().endsWith(l_startMark)) {
                            active = true;

                        }

                        if (active && !headerOutputted) {
                            out.append("<p><strong>").append(l_title != null ? l_title : l_sourceFile.getName()).append("</strong></p>");
                            out.append("<pre class=\"prettyprint");
                            if (l_linenums) out.append(" linenums:"+line);
                            out.append(" "+l_lang).append("\">");

                            headerOutputted = true;
                        }

                        if (active) {

                            if (l_mark != null && s.trim().endsWith(l_endMark)) {
                                active = false;
                            }
                            s = s.replaceFirst("(^|\\s+)\\S+(<:\\w+|\\w+:>)+\\s*$","");

                            for (int i = 0; i < s.length(); i++) {
                                final char c = s.charAt(i);
                                switch (c) {
                                    case '&':
                                        out.append("&amp;");
                                        break;
                                    case '<':
                                        out.append("&lt;");
                                        break;
                                    case '>':
                                        out.append("&gt;");
                                        break;
                                    default:
                                        out.append(c);
                                        break;
                                }
                            }
                            out.append('\n');
                        }
                    }
                    out.append("</pre>\n");



                } catch (Throwable t) {
                    out.append("[syntaxHighlight] Plugin error: ").append(t.toString());
                }
            }
        }
    }

    private String getParam(Map<String, String> params, String i_param, String i_default) {
        String l_paramValue = params.get(i_param);
        return l_paramValue == null ? i_default : l_paramValue;
    }


}
