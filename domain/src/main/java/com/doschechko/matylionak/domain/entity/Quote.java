package com.doschechko.matylionak.domain.entity;



public class Quote {

    String body;
    String author;
    String objectId;

    public Quote() {
    }

    public Quote(String body, String author, String objectId) {
        this.body = body;
        this.author = author;
        this.objectId = objectId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
