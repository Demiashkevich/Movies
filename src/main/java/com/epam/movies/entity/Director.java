package com.epam.movies.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@JsonFilter("genreFilter")
public class Director extends Entity<Long> {

    @ApiModelProperty(value = "The director first name")
    private String firstName;

    @ApiModelProperty(value = "The director last name")
    private String lastName;

    @ApiModelProperty(value = "The director movies")
    private List<Movie> movies = new ArrayList<>();

    public Director() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
