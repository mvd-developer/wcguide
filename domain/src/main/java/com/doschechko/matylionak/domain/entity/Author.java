package com.doschechko.matylionak.domain.entity;


public class Author {
    private String name;
    private String image;
    private String id;


    public Author() {
    }

    public Author(String name, String image, String id) {
        this.name = name;
        this.image = image;
        this.id = id;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
