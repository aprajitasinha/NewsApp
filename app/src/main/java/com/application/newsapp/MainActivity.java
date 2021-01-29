package com.application.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.application.newsapp.adapter.NewsAdpater;
import com.application.newsapp.api.ServiceGenerator;
import com.application.newsapp.api.response.ArticleResponse;
import com.application.newsapp.api.response.TopHeadlineResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
    private static final String TAG = "MainActivity";
    private NewsAdpater newsAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout= findViewById(R.id.swiperefresh);
        initSearchView();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initSearchView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void setRecyclerView(List<ArticleResponse> data) {
        newsAdapter = new NewsAdpater(data,MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(newsAdapter);
    }


    private void initSearchView() {
        searchApi("us","0d166396d29a40a3a567f8d29ac9e439");
    }

    private void searchApi(String country,String ApiKey) {
        serviceGenerator.getApi().searchPhoto(country,ApiKey)
                .enqueue(new Callback<TopHeadlineResponse>() {
                    @Override
                    public void onResponse(Call<TopHeadlineResponse> call, Response<TopHeadlineResponse> response) {
                        Log.e(TAG, "log: -----------------------------");
                        Log.d(TAG, "onResponse: " + response.body());

                        if(response.raw().networkResponse() != null){
                            Log.d(TAG, "onResponse: response is from NETWORK...");
                            List<TopHeadlineResponse> photos = new ArrayList<>();
                            if(response.body().getStatus().equalsIgnoreCase("ok")) {
                                    setRecyclerView(response.body().getArticles());
                                Log.d(TAG, "onResponse: response is ...");
                            }
                        }
                        else if(response.raw().cacheResponse() != null
                                && response.raw().networkResponse() == null){
                            List<TopHeadlineResponse> photos = new ArrayList<>();
                            if(response.body().getStatus().equalsIgnoreCase("ok")) {
                                setRecyclerView(response.body().getArticles());
                                Log.d(TAG, "onResponse: response is ...");
                            }
                            Log.d(TAG, "onResponse: response is from CACHE...");
                        }

//                        List<TopHeadlineResponse> photos = new ArrayList<>();
//                        if(response.body().getStatus().equalsIgnoreCase("ok")) {
//                            for (int i = 0; i < photos.size(); i++) {
//                                photos.add(response.body());
//                                setRecyclerView(photos.get(i).getArticles());
//                            }
//                            Log.d(TAG, "onResponse: response is ...");
//                        }
                    }

                    @Override
                    public void onFailure(Call<TopHeadlineResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
//                        newsAdapter.setPhotos(new ArrayList<ArticleResponse>());
                    }
                });

    }
}