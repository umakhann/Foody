package com.example.foodapp.model;

public class TypeModel {

    String keyName, fullName;
    int image;


    public TypeModel(String keyName, String name, int image) {
        this.keyName = keyName;
        this.fullName = name;
        this.image = image;
    }


    public String getKeyName() {
        return keyName;
    }

    public String getFullName() {
        return fullName;
    }

    public int getImage() {
        return image;
    }
}
