package com.example.dispatch;

public class profile {
    int imageResId1;
    String title;
    int imageResId2;

    public profile() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getImageResId2() {
        return imageResId2;
    }

    public void setImageResId2(int imageResId2) {
        this.imageResId2 = imageResId2;
    }

    public profile(int imageResId1, String title, int imageResId2) {
        this.imageResId1 = imageResId1;
        this.title = title;
        this.imageResId2 = imageResId2;
    }

    public int getImageResId1() {
        return imageResId1;
    }

    public void setImageResId1(int imageResId1) {
        this.imageResId1 = imageResId1;
    }

    @Override
    public String toString() {
        return "profile{" +
                "imageResId1=" + imageResId1 +
                ", title='" + title + '\'' +
                ", imageResId2=" + imageResId2 +
                '}';
    }
}
