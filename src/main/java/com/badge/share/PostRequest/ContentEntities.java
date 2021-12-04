package com.badge.share.PostRequest;

import java.util.List;

public class ContentEntities {

    private String entityLocation;

    private List<Thumbnails> thumbnails;

    public ContentEntities() {
    }

    public String getEntityLocation() {
        return entityLocation;
    }

    public void setEntityLocation(String entityLocation) {
        this.entityLocation = entityLocation;
    }

    public List<Thumbnails> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Thumbnails> thumbnails) {
        this.thumbnails = thumbnails;
    }
}
