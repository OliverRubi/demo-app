package com.example.movieyou.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movie {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("genre_ids")
    @Expose
    private ArrayList<Integer> genreIds = null;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;


    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer vote_count) {
        this.voteCount = vote_count;
    }

    public Integer getId() { return id; }

    public Boolean getVideo() { return video; }

    public void setVideo(Boolean video) { this.video = video; }

    public String getTitle() { return title; }

    public Double getPopularity() { return popularity; }

    public String getPosterPath() { return posterPath; }

    public String getOriginalLanguage() { return originalLanguage; }

    public String getOriginalTitle() { return originalTitle; }

    public ArrayList<Integer> getGenreIds() { return genreIds; }

    public String getBackdropPath() { return backdropPath; }

    public Boolean getAdult() { return adult; }

    public String getOverview() { return overview; }

    public String getReleaseDate() { return releaseDate; }
}
