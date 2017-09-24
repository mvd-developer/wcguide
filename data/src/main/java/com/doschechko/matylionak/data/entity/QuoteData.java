package com.doschechko.matylionak.data.entity;


import com.google.gson.annotations.SerializedName;

public class QuoteData {
    @SerializedName("quote_author")
    private String quote_author;
    @SerializedName("quote_body")
    private String quote_body;
    @SerializedName("objectId")
    private String objectId;

    @Override
    public String toString() {
        return "QuoteData{" +
                "quote_author='" + quote_author + '\'' +
                ", quote_body='" + quote_body + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }

    public String getQuote_author() {
        return quote_author;
    }

    public void setQuote_author(String quote_author) {
        this.quote_author = quote_author;
    }

    public String getQuote_body() {
        return quote_body;
    }

    public void setQuote_body(String quote_body) {
        this.quote_body = quote_body;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
