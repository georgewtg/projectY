package com.georgewiliam.project_y;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.georgewiliam.project_y.model.Account;
import com.georgewiliam.project_y.model.BaseResponse;
import com.georgewiliam.project_y.model.Post;
import com.georgewiliam.project_y.request.BaseApiService;
import com.georgewiliam.project_y.request.UtilsApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Context mContext;
    private BaseApiService mApiService;

    private List<Post> listPost = null;
    private PostArrayAdapter postArrayAdapter;

    private Menu actionBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        listView = findViewById(R.id.post_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        actionBar = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item == actionBar.findItem(R.id.add_post)) {
            moveActivity(mContext, AddPostActivity.class);
        }
        if (item == actionBar.findItem(R.id.about_me)) {
            moveActivity(this, AboutMeActivity.class);
        }
        if (item == actionBar.findItem(R.id.top_posts)) {
            getTopPosts();
        }
        if (item == actionBar.findItem(R.id.latest_posts)) {
            getRecentPosts();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getRecentPosts();
    }

    private void getRecentPosts() {
        mApiService.getRecentPosts().enqueue(new Callback<BaseResponse<List<Post>>>() {
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

    private void getTopPosts() {
        mApiService.getTopPosts().enqueue(new Callback<BaseResponse<List<Post>>>() {
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

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}