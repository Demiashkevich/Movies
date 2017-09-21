package com.epam.movies.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@JsonFilter("movieFilter")
public class Movie extends Entity<Long> {

    private String title;

    private List<Genre> genres = new ArrayList<>();

    private double metascore;

    private String description;

    private List<Director> directors = new ArrayList<>();

    private List<Star> stars = new ArrayList<>();

    private String timeDate;

    private String certificateRuntime;

    public Movie() {
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public double getMetascore() {
        return metascore;
    }

    public void setMetascore(double metascore) {
        this.metascore = metascore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public String getCertificateRuntime() {
        return certificateRuntime;
    }

    public void setCertificateRuntime(String certificateRuntime) {
        this.certificateRuntime = certificateRuntime;
    }
}

