package com.application.newsapp.interfaces;

import android.view.View;

import com.application.newsapp.api.response.ArticleResponse;
import com.application.newsapp.api.response.TopHeadlineResponse;

public interface ClickListner {
    void OnClick(ArticleResponse model, int position, View view);
}
