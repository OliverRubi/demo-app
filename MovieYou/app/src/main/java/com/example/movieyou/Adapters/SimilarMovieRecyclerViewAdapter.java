package com.example.movieyou.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Model.Movie;
import com.example.movieyou.R;
import com.example.movieyou.URLConstant;
import com.example.movieyou.ui.AboutMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarMovieRecyclerViewAdapter extends RecyclerView.Adapter<SimilarMovieRecyclerViewAdapter.MyViewHolder> {


    ArrayList<Movie> similarMovies;
    Context context;

    public SimilarMovieRecyclerViewAdapter(ArrayList<Movie> similarMovies, Context context) {
        this.similarMovies = similarMovies;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AboutMovie.class);
            Bundle bundle = new Bundle();
            intent.setClass(context, AboutMovie.class);
            intent.putExtra("movie_id", similarMovies.get(position).getId());
            intent.putExtra("movie_name", similarMovies.get(position).getTitle());
            intent.putExtra("poster_path", similarMovies.get(position).getPosterPath());
            intent.putExtra("banner_path", similarMovies.get(position).getBackdropPath());
            intent.putExtra("genres", similarMovies.get(position).getGenreIds());
            context.startActivity(intent, bundle);

        });
        if (similarMovies != null) {

            if(similarMovies.get(position).getTitle().length() >= 25) {
                String title = similarMovies.get(position).getTitle().substring(0, 25) + "...";
                holder.movieTitle.setText(title);
            }else{
                holder.movieTitle.setText(similarMovies.get(position).getTitle());
            }
            Picasso.with(context).load(URLConstant.IMAGE_POSTER_BASE_URL + similarMovies.get(position).getPosterPath()).into(holder.thumbnailPoster);
            if (similarMovies.get(position).getReleaseDate().length() >= 5) {
                String date = similarMovies.get(position).getReleaseDate().substring(0, 4);
                holder.releaseDate.setText(date);
            }
        }
    }

    @Override
    public int getItemCount() {
        return similarMovies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.movieThumbnailImageView)
        ImageView thumbnailPoster;
        @BindView(R.id.movieNameTextView)
        TextView movieTitle;
        @BindView(R.id.movieReleaseDateTextView)
        TextView releaseDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
