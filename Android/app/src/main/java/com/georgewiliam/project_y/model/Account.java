package com.georgewiliam.project_y.model;

public class Account {
    public String user_id;
    public String username;
    public String email;
    public String password;
    public String profile_img_id;

    public Account(String username, String email, String password, String profile_img_id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profile_img_id = profile_img_id;
    }

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
