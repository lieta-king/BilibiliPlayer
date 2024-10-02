package com.example.bilibiliplayer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BiliService {
    @GET("/video/{id}")
    Call<VideoResponse> getVideo(@Path("id") String videoId);
}
