package com.georgewiliam.project_y;

import static com.georgewiliam.project_y.LoginActivity.loggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.georgewiliam.project_y.model.BaseResponse;
import com.georgewiliam.project_y.model.Post;
import com.georgewiliam.project_y.request.BaseApiService;
import com.georgewiliam.project_y.request.UtilsApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;

    private List<Post> listPost;
    private PostArrayAdapter postArrayAdapter;

    private ImageView aboutMeImage;
    private TextView profileUsername, profileEmail;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        aboutMeImage = findViewById(R.id.about_me_image);
        profileUsername = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);
        listView = findViewById(R.id.profile_post_view);

        String aboutMeImageString = "https://drive.google.com/uc?id=" + loggedAccount.profile_img_id;
        Picasso.get().load(aboutMeImageString).into(aboutMeImage);

        profileUsername.setText(loggedAccount.username);
        profileEmail.setText(loggedAccount.email);
    }

    protected void onResume() {
        super.onResume();

        getPostsByUser();
    }

    private void getPostsByUser() {
        mApiService.getPostByUser(loggedAccount).enqueue(new Callback<BaseResponse<List<Post>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Post>>> call, Response<BaseResponse<List<Post>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<List<Post>> res = response.body();
                listPost = res.payload;

                postArrayAdapter = new PostArrayAdapter(mContext, listPost);
                listView.setAdapter(postArrayAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Post>>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}