package com.example.movieyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.movieyou.Dependency.App;
import com.example.movieyou.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutMovie extends AppCompatActivity {

    @BindView(R.id.aboutCotainer)
    ViewPager container;
    @BindView(R.id.nameTextView)
    TextView movieTitle;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDate;
    @BindView(R.id.runTimeTextView)
    TextView runtime;
    @BindView(R.id.movieThumbnailImageView)
    ImageView poster;
    @BindView(R.id.genreTextView)
    TextView genres;
    @BindView(R.id.ratingTextView)
    TextView rating;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    int movie_id;
    String movieName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.about_movie);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        final String posterPath = intent.getStringExtra("poster_path");
        movieName = intent.getStringExtra("movie_name");

        collapsingToolbarLayout.setTitle("About Movie");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent, getTheme()));



    }
}
