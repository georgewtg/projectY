package com.georgewiliam.project_y;

import static com.georgewiliam.project_y.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.georgewiliam.project_y.model.BaseResponse;
import com.georgewiliam.project_y.model.Comment;
import com.georgewiliam.project_y.model.Post;
import com.georgewiliam.project_y.request.BaseApiService;
import com.georgewiliam.project_y.request.UtilsApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;

    public static Post selectedPost = null;
    private List<Comment> listComment = null;
    private CommentArrayAdapter commentArrayAdapter;

    private ImageView postDetailImage;
    private EditText editComment;
    private Button commentButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        postDetailImage = findViewById(R.id.post_detail_view);
        editComment = findViewById(R.id.edit_comment);
        commentButton = findViewById(R.id.comment_button);
        listView = findViewById(R.id.comment_list);

        String postImage = "https://drive.google.com/uc?id=" + selectedPost.post_img_id;
        Picasso.get().load(postImage).into(postDetailImage);

        commentButton.setOnClickListener(v -> addComment());

        getComments();
    }

    private void getComments() {
        mApiService.getCommentByPost(selectedPost).enqueue(new Callback<BaseResponse<List<Comment>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Comment>>> call, Response<BaseResponse<List<Comment>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<List<Comment>> res = response.body();
                listComment = res.payload;

                commentArrayAdapter = new CommentArrayAdapter(mContext, listComment);
                listView.setAdapter(commentArrayAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Comment>>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addComment() {
        String commentS = editComment.getText().toString();

        if (commentS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Comment comment = new Comment(selectedPost.post_id, loggedAccount.user_id, commentS);
        mApiService.addComment(comment).enqueue(new Callback<BaseResponse<Comment>>() {
            @Override
            public void onResponse(Call<BaseResponse<Comment>> call, Response<BaseResponse<Comment>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Comment> res = response.body();
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                recreate();
            }

            @Override
            public void onFailure(Call<BaseResponse<Comment>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}