package com.georgewiliam.project_y.model;

public class BaseResponse<T> {
    public boolean success;
    public String message;
    public T payload;
}
