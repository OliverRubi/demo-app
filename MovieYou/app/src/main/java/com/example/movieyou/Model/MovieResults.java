package com.example.movieyou.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResults {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private ArrayList<Movie> movies;

    public Integer getPage() { return page; }

    public Integer getTotalResults() { return totalResults; }

    public Integer getTotalPages() { return totalPages; }

    public ArrayList<Movie> getMovies() { return movies; }
    public ArrayList<Movie> getInitialMovies() {
       ArrayList<Movie> initialMovies = new ArrayList<>();

       for(int i=0; i<3; i++)
       {
           initialMovies.set(i, movies.get(i));
       }
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
