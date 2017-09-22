package com.epam.movies.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@JsonFilter("genreFilter")
public class Genre extends Entity<Long> {
    @ApiModelProperty("The genre name")
    private String name;
    @ApiModelProperty("The genre movies")
    private List<Movie> movies = new ArrayList<>();

    public Genre() {
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
