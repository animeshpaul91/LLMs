package org.javabrains.jax.rs.messenger.model;

public class Link {
    private String url;
    private String rel;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public String toString() {
        return "Link{" +
                "url='" + url + '\'' +
                ", rel='" + rel + '\'' +
                '}';
    }
}
