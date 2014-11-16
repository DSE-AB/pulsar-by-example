package se.dse.pulsar.devtools.wiki;

import org.markdown4j.Markdown4jProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.core.api.PulsarModuleContext;
import se.dse.pulsar.core.api.extender.PulsarModuleExtender;
import se.dse.pulsar.module.modulemanager.api.ModuleManager;
import se.dse.pulsar.module.modulemanager.api.PulsarModuleRef;
import se.dse.pulsar.module.resourcemanager.api.Resource;
import se.dse.pulsar.module.resourcemanager.api.ResourceManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class WikiExtender implements PulsarModuleExtender {

    private final static Logger logger = LoggerFactory.getLogger(WikiExtender.class);

    private final Map<String, IndexedModule> index = Collections.synchronizedMap(new HashMap<String, IndexedModule>());
    private final ResourceManager resourceManager;
    private final ModuleManager moduleManager;

    private class IndexedModule {
        final PulsarModuleRef moduleRef;

        private IndexedModule(PulsarModuleRef i_moduleRef) {
            moduleRef = i_moduleRef;
        }
    }

    @Inject
    public WikiExtender(ResourceManager i_resourceManager, ModuleManager i_moduleManager) {
        resourceManager = i_resourceManager;
        moduleManager = i_moduleManager;
    }

    @Override
    public boolean extend(PulsarModuleContext i_pulsarModuleContext) {
        PulsarModuleRef l_moduleRef = moduleManager.getModuleRef(i_pulsarModuleContext.getSymbolicName());
        index.put(i_pulsarModuleContext.getSymbolicName(), new IndexedModule(l_moduleRef));
        return true;
    }

    @Override
    public void extendPostStart(PulsarModuleContext i_pulsarModuleContext) {}

    @Override
    public void unExtend(PulsarModuleContext i_pulsarModuleContext) {
        index.remove(i_pulsarModuleContext.getSymbolicName());

    }

    public String getIndex() {
        StringBuilder l_stringBuilder = new StringBuilder();
        l_stringBuilder.append("<html>");
        l_stringBuilder.append("<body>");
        l_stringBuilder.append("<ul>");
        for (String s : index.keySet()) {
            l_stringBuilder.append("<li><a href=\"index/").append(s).append("/\">").append(s).append("</a></li>");
        }
        l_stringBuilder.append("</ul>");
        l_stringBuilder.append("</body>");
        l_stringBuilder.append("</html>");
        return l_stringBuilder.toString();
    }

    public String getPageContent(String i_pagePath) throws IOException {
        String[] l_pathElements = i_pagePath.split("/");
        IndexedModule l_indexedModule = index.get(l_pathElements[0]);
        if (l_indexedModule != null) {

            String l_page = ((l_pathElements.length > 1) ? l_pathElements[l_pathElements.length-1]: "index.md");
            StringBuilder l_path = new StringBuilder();
            for (int i = 1; i<l_pathElements.length-1; i++) {
                l_path.append(l_pathElements[i]).append("/");
            }
            String l_resourcePath = "wiki/" + l_path + l_page;
            Resource l_resource = resourceManager.getResource(l_indexedModule.moduleRef,
                    ResourceManager.ResourceType.STATIC, l_resourcePath);
            if (l_resource != null) {
                return processContent(l_page, getResourceAsString(l_resource));
            } else {
                return "Path Not Found:" + l_resourcePath;
            }
        } else {
            return "Module Not Found :" + i_pagePath;
        }
    }

    private String processContent(String i_page, String i_resourceAsString) throws IOException {
        if (i_page.endsWith(".md")) {
            StringBuilder l_stringBuilder = new StringBuilder();
            l_stringBuilder.append("<html><head><link rel='stylesheet' href='/pulsar/wiki/html/css/markdown.css'/></head><body>");
            l_stringBuilder.append((new Markdown4jProcessor().process(i_resourceAsString)));
            l_stringBuilder.append("<body></html>");
            return l_stringBuilder.toString();
        } else {
            return i_resourceAsString;
        }
    }


    private String getResourceAsString(Resource i_resource) {
        StringBuilder l_stringBuilder = new StringBuilder();
        {
            char[] l_buf = new char[1024];
            int count;
            try (Reader l_reader =
                         new InputStreamReader(new BufferedInputStream(i_resource.getURL().openStream()), "UTF-8")) {
                while ((count = l_reader.read(l_buf)) > 0) {
                    l_stringBuilder.append(l_buf, 0, count);
                }
            } catch (IOException e) {
                logger.warn("[getPageContent]", e);
            }
        }
        return l_stringBuilder.toString();
    }


}
