package com.example.movieyou.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.palette.graphics.Palette;

import com.example.movieyou.Dependency.App;
import com.example.movieyou.Fragments.InfoMovieFragment;
import com.example.movieyou.GenreIDs;
import com.example.movieyou.Model.AboutMovieResponse;
import com.example.movieyou.Networks.MovieApiService;
import com.example.movieyou.R;
import com.example.movieyou.URLConstant;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMovie extends AppCompatActivity {


    @BindView(R.id.nameTextView)
    TextView movieTitle;
    @BindView(R.id.releaseDateTextView)
    TextView releaseDate;
    @BindView(R.id.runTimeTextView)
    TextView runtime;
    @BindView(R.id.thumbnail)
    ImageView poster;
    @BindView(R.id.genreTextView)
    TextView genres;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.banner)
    ImageView banner;

    int movie_id;
    String movieName;
    @Inject
    MovieApiService movieApiService;
    String posterPath;
    AboutMovieResponse aboutMovieResponse;
    ArrayList<Integer> genreIds;
    private MenuItem item;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.searchToolBar:
                intent = new Intent(AboutMovie.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.homeToolbar:
                intent = new Intent(AboutMovie.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_movie);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        movie_id = intent.getIntExtra("movie_id", 0);
        final String posterPath = intent.getStringExtra("poster_path");
        final String bannePath = intent.getStringExtra("banner_path");
        movieName = intent.getStringExtra("movie_name");
        genreIds = intent.getIntegerArrayListExtra("genres");


        collapsingToolbarLayout.setTitle("About Movie");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent, getTheme()));

        movieTitle.setText(movieName);

        movieApiService.getAboutMovie(movie_id, URLConstant.API_KEY).enqueue(new Callback<AboutMovieResponse>() {
            @Override
            public void onResponse(Call<AboutMovieResponse> call, Response<AboutMovieResponse> response) {


                runtime.setText(response.body().getRunTimeOfMovie() / 60 + "hrs " + response.body().getRunTimeOfMovie() % 60 + "mins");
                if (response.body().getReleaseDate().length() >= 5) {
                    releaseDate.setText(response.body().getReleaseDate().substring(0, 4));
                }
            }

            @Override
            public void onFailure(Call<AboutMovieResponse> call, Throwable t) {

            }
        });

        Log.i("Link", "onCreate: " + posterPath);
        Picasso.with(this).load(URLConstant.IMAGE_POSTER_BASE_URL + posterPath).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette.from(bitmap).generate(palette -> {
                    int color = palette.getDarkMutedColor(Color.parseColor("#424242"));
                    //Palette.Swatch swatch1 = palette.getDarkVibrantSwatch();
                    collapsingToolbarLayout.setBackgroundColor(color);
                    collapsingToolbarLayout.setContentScrimColor(color);

                });

                poster.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        Picasso.with(this).load(URLConstant.IMAGE_BANNER_BASE_URL+ bannePath).into(banner);

        if(genreIds != null)
        {
            GenreIDs ids = new GenreIDs();
            genres.setText("");
            for(int i=0; i<genreIds.size()-1; i++)
            {
                genres.append(ids.getGenreName(genreIds.get(i)) + ", ");
            }
            genres.append(ids.getGenreName( genreIds.get(genreIds.size()-1) ) );
        }
        else {
            Log.i("Genre", "onCreate: Hey");
        }
        InfoMovieFragment fragment = new InfoMovieFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("movieID", movie_id);
        bundle.putString("movieTitle", movieName);
        fragment.setArguments(bundle);
        transaction.replace(R.id.aboutContainer, fragment);
        transaction.commit();
    }



    private String dateGenerator(String date) {
        if (date.length() == 9 || date.length() == 10) {
            String month = date.substring(5, 7);
            String ans = "";
            switch (month) {
                case "01":
                    ans = "January";
                    break;
                case "02":
                    ans = "February";
                    break;
                case "03":
                    ans = "March";
                    break;
                case "04":
                    ans = "April";
                    break;
                case "05":
                    ans = "May";
                    break;
                case "06":
                    ans = "June";
                    break;
                case "07":
                    ans = "July";
                    break;
                case "08":
                    ans = "August";
                    break;
                case "09":
                    ans = "September";
                    break;
                case "10":
                    ans = "October";
                    break;
                case "11":
                    ans = "November";
                    break;
                case "12":
                    ans = "December";
                    break;

            }
            return (ans + " " + date.substring(8, 10) + "," + " " + date.substring(0, 4));
        } else
            return "NA";
    }



}

