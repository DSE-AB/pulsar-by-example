package se.dse.pulsar.devtools.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.devtools.wiki.api.Wiki;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WikiImpl implements Wiki {

    private final static Logger logger = LoggerFactory.getLogger(WikiImpl.class);
    private final WikiExtender wikiExtender;

    @Inject
    public WikiImpl(WikiExtender i_wikiExtender) {
        wikiExtender = i_wikiExtender;
    }

    @Override
    public String index() {
        return wikiExtender.getIndex();
    }

    @Override
    public String page(String i_pagePath) {
        try {
            return wikiExtender.getPageContent(i_pagePath);
        } catch (Throwable t) {
            logger.warn("[page] failed to get page {}", i_pagePath, t);
            return "Failed to get page: "+t.toString();
        }
    }
}
