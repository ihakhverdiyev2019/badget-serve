package com.badge.share.PostRequest;

import java.util.List;

public class Content {

    private List<ContentEntities> contentEntities;
    private String title;

    public Content() {
    }

    public List<ContentEntities> getContentEntities() {
        return contentEntities;
    }

    public void setContentEntities(List<ContentEntities> contentEntities) {
        this.contentEntities = contentEntities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
