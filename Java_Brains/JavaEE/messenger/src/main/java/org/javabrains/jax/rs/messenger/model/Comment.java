package org.javabrains.jax.rs.messenger.model;

import java.util.Date;

// Comment is not annotated with a @XmlRootElement because it's existence is solely within Message
public class Comment {
    private long id;
    private String Message;
    private Date created;
    private String author;

    public Comment() {

    }

    public Comment(long id, String message, String author) {
        this.id = id;
        Message = message;
        this.author = author;
        this.created = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", Message='" + Message + '\'' +
                ", created=" + created +
                ", author='" + author + '\'' +
                '}';
    }
}
