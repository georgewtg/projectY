package com.georgewiliam.project_y;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.georgewiliam.project_y.model.BaseResponse;
import com.georgewiliam.project_y.model.Post;
import com.georgewiliam.project_y.request.BaseApiService;
import com.georgewiliam.project_y.request.UtilsApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private Uri imageUri = null;
    private String imageId = null;
    Bitmap mBitmap;

    private Context mContext;
    private BaseApiService mApiService;

    private Button selectImageButton, postButton;
    private ImageView postPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        selectImageButton = findViewById(R.id.select_image_button);
        postButton = findViewById(R.id.post_button);
        postPreview = findViewById(R.id.post_view);

        selectImageButton.setOnClickListener(v -> openGallery());
        postButton.setOnClickListener(v -> uploadImage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            mBitmap = BitmapFactory.decodeFile(FileUtils.getPath(mContext, imageUri));
            postPreview.setImageURI(imageUri);
        }
    }

    private void addNewPost() {
        String user_id = LoginActivity.loggedAccount.user_id;
        Post post = new Post(user_id, imageId);
        mApiService.addPost(post).enqueue(new Callback<BaseResponse<Post>>() {
            @Override
            public void onResponse(Call<BaseResponse<Post>> call, Response<BaseResponse<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Post> res = response.body();
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<BaseResponse<Post>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void uploadImage() {
        try {
            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            OutputStream os = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

            mApiService.uploadPost(body).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "Application error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    imageId = response.body();
                    addNewPost();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}