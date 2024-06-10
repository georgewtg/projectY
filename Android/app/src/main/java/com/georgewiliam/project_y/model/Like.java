package com.georgewiliam.project_y.model;

public class Like {
    public int like_id;
    public int post_id;
    public String user_id;

    public Like(int post_id, String user_id) {
        this.post_id = post_id;
        this.user_id = user_id;
    }
}
