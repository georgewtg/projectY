package com.georgewiliam.project_y.model;

public class Account {
    private String user_id;
    public String username;
    public String email;
    public String password;

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
