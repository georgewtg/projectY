package com.georgewiliam.project_y.model;

public class Following {
    public int following_id;
    public String follower_id;
    public String followed_id;

    public Following(String follower_id, String followed_id) {
        this.follower_id = follower_id;
        this.followed_id = follower_id;
    }
}
