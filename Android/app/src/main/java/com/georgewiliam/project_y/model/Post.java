package com.georgewiliam.project_y.model;

import java.sql.Timestamp;

public class Post {
    public int post_id;
    public String user_id;
    public String post_img_id;
    public Timestamp post_time;
    public String username;
    public String email;
    public String profile_img_id;

    public Post(String user_id, String post_img_id) {
        this.user_id = user_id;
        this.post_img_id = post_img_id;
    }
}
