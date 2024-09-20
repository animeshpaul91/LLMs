package org.javabrains.jax.rs.messenger.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@XmlRootElement
public class Message {
    private long id;
    private String message;
    private Date created;
    private String author;
    private Map<Long, Comment> comments;
    private List<Link> links;
    private Optional<String> customerSid = Optional.empty();

    public Message() { // used by Jackson
    }

    public Message(long id, String message, String author) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.created = new Date();
        this.comments = new HashMap<>();
        this.links = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlTransient
    // to ignore the comments when the message list is converted to JSON for /GET messages and /GET message ID
    public Map<Long, Comment> getComments() {
        return comments;
    }

    public void setComments(Map<Long, Comment> comments) {
        this.comments = comments;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Optional<String> getCustomerSid() {
        return customerSid;
    }

    public void setCustomerSid(Optional<String> customerSid) {
        this.customerSid = customerSid;
    }

    public void addLink(String url, String rel) {
        Link link = new Link();
        link.setUrl(url);
        link.setRel(rel);
        links.add(link);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", created=" + created +
                ", author='" + author + '\'' +
                ", comments=" + comments +
                ", links=" + links +
                ", customerSid=" + customerSid +
                '}';
    }
}
