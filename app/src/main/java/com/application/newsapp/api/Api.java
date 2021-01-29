package com.application.newsapp.api;

import com.application.newsapp.api.response.TopHeadlineResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {



    @GET("top-headlines")
    Call<TopHeadlineResponse>searchPhoto(@Query("country") String leaveType, @Query("apiKey") String language);

}










