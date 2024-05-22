package com.georgewiliam.project_y.request;

import com.georgewiliam.project_y.model.Account;
import com.georgewiliam.project_y.model.BaseResponse;
import com.georgewiliam.project_y.model.StatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {
    //account
    @POST("/register")
    Call<BaseResponse<Account>> register (@Body Account account);

    @GET("/status")
    Call<StatusResponse> getStatus();
}
