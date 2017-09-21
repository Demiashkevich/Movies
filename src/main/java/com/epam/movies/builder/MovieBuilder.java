package com.epam.movies.builder;

import com.epam.movies.entity.Director;
import com.epam.movies.entity.Genre;
import com.epam.movies.entity.Movie;
import com.epam.movies.entity.Star;

import java.util.ArrayList;
import java.util.List;

public class MovieBuilder extends Builder {

    private Movie movie = new Movie();

    public void buildId(final long id) {
        movie.setId(id);
    }

    public void buildTitle(final String titleString) {
        movie.setTitle(titleString);
    }

    public void buildCertRuntime(final String certRuntimeString) {
        movie.setCertificateRuntime(certRuntimeString);
    }

    public void buildTimeDate(final String timeDateString) {
        movie.setTimeDate(timeDateString);
    }

    public void buildDirectors(final List<String> directorsString) {
        long directorId = 0;
        final List<Director> directors = new ArrayList<>();
        for (final String directorString : directorsString) {
            final Director director = new Director();
            final String[] partName = directorString.split(" ");
            if (partName.length == 2) {
                director.setId(++directorId);
                director.setFirstName(partName[0]);
                director.setLastName(partName[1]);
                directors.add(director);
            }
        }
        movie.setDirectors(directors);
    }

    public void buildStars(final List<String> starsString) {
        long starId = 0;
        final List<Star> stars = new ArrayList<>();
        for (String starString : starsString) {
            final Star star = new Star();
            final String[] partName = starString.split(" ");
            if (partName.length == 2) {
                star.setId(++starId);
                star.setFirstName(partName[0]);
                star.setLastName(partName[1]);
                stars.add(star);
            }
        }
        movie.setStars(stars);
    }

    public void buildDescription(final String descriptionsString) {
        movie.setDescription(descriptionsString);
    }

    public void buildGenres(final List<String> genresString) {
        long genreId = 0;
        final List<Genre> genres = new ArrayList<>();
        for (final String genreString : genresString) {
            final Genre genre = new Genre();
            genre.setId(++genreId);
            genre.setName(genreString);
            genres.add(genre);
        }
        movie.setGenres(genres);
    }

    public void buildMetascope(final String metascopeString) {
        if (metascopeString != null && !metascopeString.isEmpty()) {
            final Double metascope = Double.parseDouble(metascopeString);
            movie.setMetascore(metascope);
        }
    }

    public Movie getMovie() {
        return movie;
    }

}
