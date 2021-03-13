package com.example.imma;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.imma.parameter.Articles;

import java.util.List;
import java.util.Locale;

import static com.example.imma.Utils.getRandomDrawableColor;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Articles> articles;
    private OnItemClickListener onItemClickListener;
    public Adapter (Context context, List<Articles> articles)
    {
        this.context=context;
        this.articles=articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Articles art=articles.get(position);
        String url=art.getUrl();
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(getRandomDrawableColor());
        requestOptions.error(getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        requestOptions.timeout(3000);

        Glide.with(context).load(art.getUrlToImage()).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.GONE);
                return false;
            }
        })
                .transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);

        holder.newsTitle.setText(art.getTitle());
        holder.descrip.setText(art.getDescription());
        holder.source.setText(art.getSource().getName());
        holder.author.setText(art.getAuthor());
        holder.time.setText("\u2020" + Utils.DateToTimeFormat(art.getPublishedAt()));
        holder.newsDate.setText(Utils.DateFormat(art.getPublishedAt()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsInDetails.class);
                intent.putExtra("url", art.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView newsTitle, newsDate, author, descrip, time, source;
        CardView cardView;
        ProgressBar progressBar;
        ImageView imageView;
        OnItemClickListener onItemClickListener;
        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            author=itemView.findViewById(R.id.author);
            descrip=itemView.findViewById(R.id.desc);
            source=itemView.findViewById(R.id.source);
            time=itemView.findViewById(R.id.time);
            progressBar=itemView.findViewById(R.id.load_photo);
            newsDate=itemView.findViewById(R.id.publishedAt);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            cardView=itemView.findViewById(R.id.cardView);
            imageView=itemView.findViewById(R.id.img);

            this.onItemClickListener=onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public String getCountry(){
        Locale locale=Locale.getDefault();
        String country=locale.getCountry();
        return country.toLowerCase();
    }
}
