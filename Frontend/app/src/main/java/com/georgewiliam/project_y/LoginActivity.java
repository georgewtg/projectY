package com.georgewiliam.project_y;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.georgewiliam.project_y.model.StatusResponse;
import com.georgewiliam.project_y.request.BaseApiService;
import com.georgewiliam.project_y.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private Button loginButton = null;
    private TextView register = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        register = findViewById(R.id.register);
        loginButton = findViewById(R.id.login_button);

        register.setOnClickListener(v -> moveActivity(this, RegisterActivity.class));
        loginButton.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        mApiService.getStatus().enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse statusResponse = response.body();
                Toast.makeText(mContext, statusResponse.getStatus(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(mContext, "problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}