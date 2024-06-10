package com.georgewiliam.project_y.request;

import com.georgewiliam.project_y.model.Account;
import com.georgewiliam.project_y.model.Post;
import com.georgewiliam.project_y.model.Comment;
import com.georgewiliam.project_y.model.Like;
import com.georgewiliam.project_y.model.Following;
import com.georgewiliam.project_y.model.BaseResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface BaseApiService {
    // account
    @POST("/register")
    Call<BaseResponse<Account>> register(@Body Account account);

    @POST("/login")
    Call<BaseResponse<Account>> login(@Body Account account);

    @POST("/getAccountById")
    Call<BaseResponse<Account>> getAccountById(@Body String user_id);

    @PUT("/editaccount")
    Call<BaseResponse<Account>> editAccount(@Body Account account);

    @DELETE("/deleteAccount")
    Call<BaseResponse<Account>> deleteAccount(@Body Account account);


    // post
    @POST("/addPost")
    Call<BaseResponse<Post>> addPost(@Body Post post);

    @GET("/getAllPosts")
    Call<BaseResponse<List<Post>>> getAllPosts();

    @POST("/getPostById")
    Call<BaseResponse<Post>> getPostById(@Body int post_id);

    @POST("/getPostByUser")
    Call<BaseResponse<List<Post>>> getPostByUser(@Body Account account);

    @GET("/getTopPosts")
    Call<BaseResponse<List<Post>>> getTopPosts();

    @GET("/getRecentPosts")
    Call<BaseResponse<List<Post>>> getRecentPosts();

    @DELETE("/deletePost")
    Call<BaseResponse<Post>> deletePost(@Body int post_id);


    // comment
    @POST("/addComment")
    Call<BaseResponse<Comment>> addComment(@Body Comment comment);

    @PUT("/editComment")
    Call<BaseResponse<Comment>> editComment(@Body Comment comment);

    @GET("/getAllComments")
    Call<BaseResponse<List<Comment>>> getAllComments();

    @POST("/getCommentByPost")
    Call<BaseResponse<List<Comment>>> getCommentByPost(@Body Post post);

    @POST("/getCommentByUser")
    Call<BaseResponse<List<Comment>>> getCommentByUser(@Body String user_id);

    @DELETE("/deleteComment")
    Call<BaseResponse<Comment>> deleteComment(@Body int comment_id);


    // like
    @POST("/addLike")
    Call<BaseResponse<Like>> addLike(@Body Like like);

    @GET("/getAllLikes")
    Call<BaseResponse<List<Like>>> getAllLikes();

    @POST("/getLikeByPost")
    Call<BaseResponse<List<Like>>> getLikeByPost(@Body int post_id);

    @POST("/getLikeByUser")
    Call<BaseResponse<List<Like>>> getLikeByUser(@Body String user_id);

    @DELETE("/deleteLike")
    Call<BaseResponse<Like>> deleteLike(@Body int like_id);


    // following
    @POST("/addFollowing")
    Call<BaseResponse<Following>> addFollowing(@Body Following following);

    @GET("/getAllFollowings")
    Call<BaseResponse<List<Following>>> getAllFollowings();

    @POST("/getFollowers")
    Call<BaseResponse<List<String>>> getFollowers(@Body String user_id);

    @POST("/getFolloweds")
    Call<BaseResponse<List<String>>> getFolloweds(@Body String user_id);

    @DELETE("/deleteFollowing")
    Call<BaseResponse<Following>> deleteFollowing(@Body int following_id);


    // google
    @Multipart
    @POST("/uploadProfile")
    Call<String> uploadProfile (@Part MultipartBody.Part file);

    @Multipart
    @POST("/uploadPost")
    Call<String> uploadPost (@Part MultipartBody.Part file);
}
