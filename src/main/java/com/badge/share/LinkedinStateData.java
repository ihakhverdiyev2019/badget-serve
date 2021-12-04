package com.badge.share;

import java.util.List;

public class LinkedinStateData {

    private String text;

    private String subtitle;

    private String postClickUrl;

    private List<Image> imageUrl;

    public LinkedinStateData() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPostClickUrl() {
        return postClickUrl;
    }

    public void setPostClickUrl(String postClickUrl) {
        this.postClickUrl = postClickUrl;
    }

    public List<Image> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<Image> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
