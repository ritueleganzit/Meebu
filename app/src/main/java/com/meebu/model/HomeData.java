package com.meebu.model;

/**
 * Created by eleganz on 1/3/19.
 */

public class HomeData {
    String title;
    int image;

    public HomeData(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
