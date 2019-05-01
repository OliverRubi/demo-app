package com.example.movieyou.ui;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieyou.Adapters.EndlessRecyclerViewScrollListener;
import com.example.movieyou.Adapters.HomeMovieListRecyclerAdapter;
import com.example.movieyou.Dependency.App;
import com.example.movieyou.Model.Movie;
import com.example.movieyou.Model.MovieResults;
import com.example.movieyou.Networks.MovieApiService;
import com.example.movieyou.R;
import com.example.movieyou.URLConstant;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllSimilarMovies extends AppCompatActivity {

    private EndlessRecyclerViewScrollListener scrollListener;
    HomeMovieListRecyclerAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    String movieType;
    ArrayList<Movie> movieArrayList;
    @BindView(R.id.movieTypeTextView)
    TextView movieTypeText;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @Inject
    MovieApiService apiService;


    private int movieID;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_content_movie);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        Slide slide = new Slide(Gravity.BOTTOM);
        getWindow().setEnterTransition(slide);
        getWindow().setAllowEnterTransitionOverlap(true);


        Intent intent = getIntent();
        movieType = intent.getStringExtra("MOVIETYPE");
        movieID = intent.getIntExtra("movieID", 0);
        final String name = intent.getStringExtra("movieTitle");

        this.setTitle("");
        movieTypeText.setText("Movies like\n\"" + name + "\"");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.movieListRecyclerView);
        movieArrayList = new ArrayList<Movie>();

        recyclerViewAdapter = new HomeMovieListRecyclerAdapter(this, movieArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        final LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        showProgress();
        loadData(1);
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadData(page);
            }

        };

        recyclerView.addOnScrollListener(scrollListener);


    }


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

    }

    private void showData() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void loadData(int page) {


        apiService.getSimilarMovies(movieID, URLConstant.API_KEY, page).enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                ArrayList<Movie> movieList = response.body().getMovies();

                Log.i("Movie size", "onResponse: " + String.valueOf(movieList.size()));

                if (movieList == null) {
                    return;
                }

                for(Movie movie : movieList){
                    movieArrayList.add(movie);
                }

                recyclerViewAdapter.notifyDataSetChanged();
                showData();


            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });


    }
}