package se.dse.pulsarbyexample.chatter.api;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * reference: https://jaxb.java.net/guide/Mapping_interfaces.html
 */

@XmlRootElement(name = "message")
public final class ChatterMessage {

    @XmlAttribute(name = "timestamp")
    private long timestamp;

    @XmlAttribute(name = "userId")
    private String userId;

    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "content")
    private String content;


    public ChatterMessage(long i_timestamp, String i_content, String i_userId) {
        timestamp = i_timestamp;
        content = i_content;
        id = UUID.randomUUID().toString();
        userId = i_userId;
    }

    private ChatterMessage() { /* default constuctor required by JAXB */ }

    public long getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

}
