package com.rsc.ndcvc.models;

public class MCmds {
    private String action;
    private String user;

    public MCmds(String action, String user) {
        this.action = action;
        this.user = user;
    }

    public MCmds() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
