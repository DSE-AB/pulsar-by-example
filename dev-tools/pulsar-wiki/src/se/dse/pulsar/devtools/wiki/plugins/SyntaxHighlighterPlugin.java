package se.dse.pulsar.devtools.wiki.plugins;

import org.markdown4j.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class SyntaxHighlighterPlugin extends Plugin {

    private final File rootDir;
    private final String basePath = "/pulsar/wiki/html/syntaxhighlighter-3.0.83";

    public SyntaxHighlighterPlugin(File i_rootDir) {
        super("syntaxHighlight");
        rootDir = i_rootDir;
    }

    @Override
    public void emit(StringBuilder out, List<String> lines, Map<String, String> params) {
        if (getParam(params, "install", null) != null) {
            out.append("<script type=\"text/javascript\" src=\""+basePath+"/js/shCore.js\"></script>");
            out.append("<link href=\""+basePath+"/css/shCore.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.append("<link href=\""+basePath+"/css/shThemeDefault.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.append("<script type=\"text/javascript\">\n" +
                    "     SyntaxHighlighter.all()\n" +
                    "</script>");
        } else {
            String l_filePath = getParam(params, "file", null);
            String l_brush = getParam(params, "brush", "text");
            if (l_filePath != null) {
                try (BufferedReader l_reader = new BufferedReader(new FileReader(new File(rootDir, l_filePath)))) {

                    out.append("<script type=\"syntaxhighlighter\" class=\"brush: ")
                            .append(l_brush).append("\"><![CDATA[");
                    String l_line;
                    while ((l_line = l_reader.readLine()) != null) {
                        out.append(l_line);
                    }
                    out.append("]]></script>");

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
