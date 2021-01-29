package com.application.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.application.newsapp.R;
import com.application.newsapp.adapter.NewsAdpater;
import com.application.newsapp.api.ServiceGenerator;
import com.application.newsapp.api.response.ArticleResponse;
import com.application.newsapp.api.response.TopHeadlineResponse;
import com.application.newsapp.databinding.ActivityMainBinding;
import com.application.newsapp.interfaces.ClickListner;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
    private static final String TAG = "MainActivity";
    private NewsAdpater newsAdapter;
    Dialog dialog_progress;
ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initSearchView();


        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initSearchView();
                binding.swiperefresh.setRefreshing(false);
            }
        });
    }


    private void setRecyclerView(List<ArticleResponse> data) {
        newsAdapter = new NewsAdpater(data, MainActivity.this, new ClickListner() {
            @Override
            public void OnClick(ArticleResponse model, int position, View view) {

                Intent intent = new Intent(getBaseContext(), NewsDetailsActivity.class);
                intent.putExtra("TIME", model.getPublishedAt());
                intent.putExtra("TITLE", model.getTitle());
                intent.putExtra("AUTHOR", model.getAuthor());
                intent.putExtra("NAME", model.getSource().getName());
                intent.putExtra("DESC", model.getDescription());
                intent.putExtra("CONTENT", model.getContent());
                intent.putExtra("URL", model.getUrl());
                intent.putExtra("IMAGEURL", model.getUrlToImage());
                startActivity(intent);

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(newsAdapter);
    }


    private void initSearchView() {
        searchApi("us", "0d166396d29a40a3a567f8d29ac9e439");
    }

    private void searchApi(String country, String ApiKey) {


        serviceGenerator.getApi().searchPhoto(country, ApiKey)
                .enqueue(new Callback<TopHeadlineResponse>() {
                    @Override
                    public void onResponse(Call<TopHeadlineResponse> call, Response<TopHeadlineResponse> response) {
                        Log.e(TAG, "log: --");
                        if (response.isSuccessful()) {
                            try {
                                if (response.raw().networkResponse() != null) {
                                    showloader();
                                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                        hideloader();
                                        setRecyclerView(response.body().getArticles());
                                    } else {
                                        hideloader();
                                    }
                                    Log.d(TAG, "onResponse: response is from NETWORK...");
                                }

                                else if (response.raw().cacheResponse() != null
                                        && response.raw().networkResponse() == null) {
                                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                                        setRecyclerView(response.body().getArticles());
                                        Log.d(TAG, "onResponse: response is ...");
                                    }
                                    Log.d(TAG, "onResponse: response is from CACHE...");
                                }

                            } catch (Exception e) {
                                snack_for_failed();

                            }
                        } else if (response.code() == 400) {
                            snack_for_failed();

                        } else {
                            snack_for_failed();
                        }
                    }
                    @Override
                    public void onFailure(Call<TopHeadlineResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                          hideloader();
                        snack_for_failed();

                    }
                });
    }

    void snack_for_failed() {
        @SuppressLint("ResourceAsColor") Snackbar snackbar = Snackbar
                .make(binding.recyclerView, "No or slow internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(R.color.white)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        searchApi("us", "0d166396d29a40a3a567f8d29ac9e439");

                    }
                });
        snackbar.show();

    }


    public void showloader() {
        dialog_progress = new Dialog(this);
        dialog_progress.setContentView(R.layout.dialog_prog);
        dialog_progress.getWindow().setBackgroundDrawableResource(R.drawable.back);
        dialog_progress.setCancelable(false);
        dialog_progress.show();
    }
    public void hideloader() {
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        dialog_progress.hide();
                    }
                }, 700);
    }

}