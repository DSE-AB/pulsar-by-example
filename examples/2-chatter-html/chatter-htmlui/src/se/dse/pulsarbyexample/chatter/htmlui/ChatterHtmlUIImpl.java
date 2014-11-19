package se.dse.pulsarbyexample.chatter.htmlui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dse.pulsar.module.sessionmanager.api.Session;
import se.dse.pulsar.module.sessionmanager.api.SessionManager;
import se.dse.pulsar.module.util.api.JAXBUtil;
import se.dse.pulsar.module.util.api.XMLRepresentation;
import se.dse.pulsar.module.util.api.XMLRepresentationImpl;
import se.dse.pulsarbyexample.chatter.api.ChatterException;
import se.dse.pulsarbyexample.chatter.api.ChatterMessage;
import se.dse.pulsarbyexample.chatter.api.ChatterServer;
import se.dse.pulsarbyexample.chatter.htmlui.api.ChatterHtmlUI;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class ChatterHtmlUIImpl implements ChatterHtmlUI {

    private final static Logger logger = LoggerFactory.getLogger(ChatterHtmlUIImpl.class);

    private final static String CHATTER_TOKEN_KEY = "_CHATTER_TOKEN";

    private final ChatterServer chatterServer;
    private final SessionManager sessionManager;

    @Inject
    public ChatterHtmlUIImpl(ChatterServer i_chatterServer, SessionManager i_sessionManager) {
        chatterServer = i_chatterServer;
        sessionManager = i_sessionManager;
    }

    @XmlRootElement(name = "chatter")
    private static class Chatter {

        @XmlElementWrapper(name = "messages")
        @XmlElement(name = "message")
        private List<ChatterMessage> chatterMessages;

        private Chatter(List<ChatterMessage> i_chatterMessages) {
            chatterMessages = i_chatterMessages;
        }

        private Chatter() { /* default constuctor required by JAXB */ }
    }

    @Override
    public void login(String i_userName) {
        Session l_session = sessionManager.getCurrentSession();
        l_session.putValue(CHATTER_TOKEN_KEY, chatterServer.login(i_userName));
    }

    @Override
    public XMLRepresentation getChatter() {

        XMLRepresentationImpl l_xml = new XMLRepresentationImpl();

        Chatter l_messages = new Chatter(chatterServer.getChatter(0, Integer.MAX_VALUE));

        JAXBUtil l_jaxbUtil = new JAXBUtil();
        try {
            l_xml.append(l_jaxbUtil.marshal(l_messages));
        } catch (JAXBException e) {
            logger.warn("[getModules]", e);
        }
        logger.debug("[getChatter] xml: {}", l_xml.getXML());
        return l_xml;
    }

    @Override
    public void say(String i_message) {
        Session l_session = sessionManager.getCurrentSession();
        String l_chatterToken = (String)l_session.getValue(CHATTER_TOKEN_KEY);
        try {
            chatterServer.chat(l_chatterToken, i_message);
        } catch (ChatterException e) {
            logger.warn("[say] failed to send chat message: {}", e);
        }

    }
}
