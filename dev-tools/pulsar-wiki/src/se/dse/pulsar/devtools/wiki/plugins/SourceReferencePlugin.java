package se.dse.pulsar.devtools.wiki.plugins;

import org.markdown4j.CodeBlockEmitter;
import org.markdown4j.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SourceReferencePlugin extends Plugin {

    private final File rootDir;

    public SourceReferencePlugin(File i_rootDir) {
        super("sourceRef");
        rootDir = i_rootDir;
    }

    @Override
    public void emit(StringBuilder out, List<String> lines, Map<String, String> params) {
        //read params and manage default value
        String type = params.get("type");
        if(type == null) {
            type = "defaultType";
        }
        String l_filePath = getParam(params, "file", null);
        if (l_filePath != null) {
            try (BufferedReader l_reader = new BufferedReader(new FileReader(new File(rootDir, l_filePath)))) {

                List<String> l_lines = new LinkedList<>();
                String l_line;
                while ((l_line = l_reader.readLine()) != null) {
                    l_lines.add(l_line);
                }

                CodeBlockEmitter l_codeBlockEmitter = new CodeBlockEmitter();
                l_codeBlockEmitter.emitBlock(out, l_lines, "");
            } catch (Throwable t) {
                out.append("[sourceRef] Plugin error: ").append(t.toString());
            }
        }
    }

    private String getParam(Map<String, String> params, String i_param, String i_default) {
        String l_paramValue = params.get(i_param);
        return l_paramValue == null ? i_default : l_paramValue;
    }
}
