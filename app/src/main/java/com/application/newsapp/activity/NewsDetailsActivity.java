package com.application.newsapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.application.newsapp.R;
import com.application.newsapp.databinding.NewsDetailsActivityBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class NewsDetailsActivity extends AppCompatActivity {
    NewsDetailsActivityBinding binding;
    Context context;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.news_details_activity);
        context = NewsDetailsActivity.this;
        activity = NewsDetailsActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String time = extras.getString("TIME");
            String title = extras.getString("TITLE");
            String name = extras.getString("NAME");
            String desc = extras.getString("DESC");
            String content = extras.getString("CONTENT");
            String url = extras.getString("URL");
            String imageurl = extras.getString("IMAGEURL");
            String auther = extras.getString("AUTHOR");
            if (time != null) {

                String approved = month(time);

                binding.tvTime.setText(approved);
            }
            if (name != null) {
                binding.tvName.setText(name);
            }
            if (auther != null) {
                binding.tvAuthor.setText(auther +" - ");
            }
            if (imageurl != null) {
                Picasso.with(context).load(imageurl).into(binding.ivImage);
            }
            if (title != null) {
                binding.tvTitle.setText(title);
            }
            if (desc != null) {
                binding.tvDesc.setText(desc);
            }
            if (content != null) {
                binding.tvContent.setText(content);
            }
            if (url != null) {
                binding.tvUrl.setText(url);
                binding.tvUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri webpage = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }
                });

//                    }
            }


            //The key argument here must match that used in the other activity
        }

    }

    public static String month(String dates) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        System.out.println(formattedDate); // prints 10-04-2018

        return formattedDate;
    }

}
