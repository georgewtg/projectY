package com.georgewiliam.project_y.model;

public class Comment {
    public int comment_id;
    public int post_id;
    public String user_id;
    public String comment;
    public boolean is_edited;
    public String username;
    public String email;
    public String profile_img_id;

    public Comment(int post_id, String user_id, String comment) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.comment = comment;
    }
}
