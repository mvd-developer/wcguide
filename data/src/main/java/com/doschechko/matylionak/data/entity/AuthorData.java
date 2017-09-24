package com.doschechko.matylionak.data.entity;


import com.google.gson.annotations.SerializedName;

public class AuthorData {

    /**
     * Class of Data Layer that provides an Object of Author
     */

    @SerializedName("name")
    private  String name;
    @SerializedName("image")
    private  String image;
    @SerializedName("objectId")
    private  String objectId;

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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
