package com.epam.movies.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@JsonFilter("starFilter")
public class Star extends Entity<Long> {
    @ApiModelProperty("The star first name")
    private String firstName;
    @ApiModelProperty("The star last name")
    private String lastName;
    @ApiModelProperty("The star movies")
    private List<Movie> movies = new ArrayList<>();

    public Star() {
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
}
