package com.rsc.ndcvc.models;

public class MMsg {
    private String name;
    private String body;

    public MMsg(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public MMsg() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
