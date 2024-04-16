package com.djawnstj.mvcframework.use;

public class User {
    private final String id;
    private final String pw;

    public User(final String id, final String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
