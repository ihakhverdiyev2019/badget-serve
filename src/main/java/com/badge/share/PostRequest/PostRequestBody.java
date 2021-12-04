package com.badge.share.PostRequest;

public class PostRequestBody {


    private Content content;


    private String owner;
    private String subject;
    private Text text;

    public PostRequestBody() {
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }



    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }


}
