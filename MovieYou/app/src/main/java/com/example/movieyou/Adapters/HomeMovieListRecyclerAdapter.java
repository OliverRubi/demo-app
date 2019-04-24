package com.example.movieyou.Adapters;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeMovieListRecyclerAdapter extends RecyclerView.Adapter<HomeMovieListRecyclerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    public HomeMovieListRecyclerAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (movies != null) {

            if(movies.get(position).getTitle().length() >= 25) {
                String title = movies.get(position).getTitle().substring(0, 25) + "...";
                holder.movieTitle.setText(title);
            }else{
                holder.movieTitle.setText(movies.get(position).getTitle());
            }
            Picasso.with(context).load(URLConstant.IMAGE_BASE_URL + movies.get(position).getPosterPath()).into(holder.thumbnailPoster);
            if (movies.get(position).getReleaseDate().length() >= 5) {
                String date = movies.get(position).getReleaseDate().substring(0, 4);
                holder.releaseDate.setText(date);
            }
        }
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

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
