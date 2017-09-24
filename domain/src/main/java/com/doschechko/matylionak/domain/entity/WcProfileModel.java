package com.doschechko.matylionak.domain.entity;

/**
 * Created by ya
 * on 14.09.2017.
 */

public class WcProfileModel {

    private String address;
    private String coordinats;
    private String cost;
    private String work_time;
    private String objectId;
    private String image;

    public String getCoordinats() {
        return coordinats;
    }

    public void setCoordinats(String coordinats) {
        this.coordinats = coordinats;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "WcProfileModel{" +
                "address='" + address + '\'' +
                ", coordinats='" + coordinats + '\'' +
                ", cost='" + cost + '\'' +
                ", work_time='" + work_time + '\'' +
                ", objectId='" + objectId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
