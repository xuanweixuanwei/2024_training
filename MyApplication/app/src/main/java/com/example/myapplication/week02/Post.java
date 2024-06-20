package com.example.myapplication.week02;


public class Post {
    private Integer id;
    private Integer userid;
    private String title;
    private String body;

    public Post() {
        userid = 0;
        id = 0;
        title = "";
        body = "";
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userId) {
        this.userid = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
