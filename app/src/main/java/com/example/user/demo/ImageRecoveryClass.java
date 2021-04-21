package com.example.user.demo;

public class ImageRecoveryClass {

    String property_id;
    String image;

    public ImageRecoveryClass(String property_id, String image) {
        this.property_id = property_id;
        this.image = image;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
