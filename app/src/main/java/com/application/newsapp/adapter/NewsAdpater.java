package com.application.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.application.newsapp.R;
import com.application.newsapp.api.response.ArticleResponse;
import com.application.newsapp.databinding.ItemNewsBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdpater extends RecyclerView.Adapter<NewsAdpater.ViewHolder> {
    private List<ArticleResponse> list;
    Context context;
    int pos=-1;
    public NewsAdpater(List<ArticleResponse> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ArticleResponse planModel = list.get(position);
       holder.binding.tvName.setText(list.get(position).getSource().getName());
       holder.binding.tvAuthor.setText(list.get(position).getAuthor());
       holder.binding.tvTitle.setText(list.get(position).getTitle());
        Picasso.with(context)
                .load(list.get(position).getUrlToImage())
                .error(R.drawable.add_image)
                .into(holder.binding.image, new com.squareup.picasso.Callback() {

                            @Override
                            public void onSuccess() {
                                //   holder.progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        }
                );

//        if(pos==position){
//        }
//        else{
//            holder.binding.llCard.setBackground(context.getResources().getDrawable(R.drawable.backgroundd_card));
//        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ItemNewsBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= DataBindingUtil.bind(itemView);
        }
    }
}
