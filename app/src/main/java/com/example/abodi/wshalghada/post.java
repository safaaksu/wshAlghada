package com.example.abodi.wshalghada;

import android.graphics.Bitmap;

public class post {
    Bitmap image;
    String name;
    int edit;
    int delete;

    public post() {
    }

    public post(Bitmap image, String name, int edit, int delete) {
        this.image = image;
        this.name = name;
        this.edit = edit;
        this.delete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getEdit() {
        return edit;
    }

    public void setEdit(int edit) {
        this.edit = edit;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
