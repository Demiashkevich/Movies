package com.epam.movies.controller;

import com.epam.movies.entity.Director;
import com.epam.movies.entity.Genre;
import com.epam.movies.entity.Movie;
import com.epam.movies.entity.Star;
import com.epam.movies.filter.MovieFilter;
import com.epam.movies.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@RestController
public class MovieControllerImpl implements MovieController {

  private static final Logger logger = Logger.getLogger(MovieControllerImpl.class);

  @Autowired
  private MovieService movieService;

  private ObjectMapper mapper = new ObjectMapper();
  private ObjectWriter writer;

  @GetMapping(value = "/movies/{movieId}")
  public ResponseEntity<String> readMovie(final @PathVariable long movieId,
                                          final @RequestParam(required = false) String[] include) {
    Movie movie = movieService.read(movieId);
    String result = this.writer("movieFilter", movie, include);
    if (result == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies")
  public ResponseEntity<String> readMovie(final @RequestParam(required = false) String[] include) {
    Set<Movie> movies = movieService.read();
    String result = this.writer("movieFilter", movies, include);
    if (result == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies/{movieId}/genres/{genreId}")
  public ResponseEntity<String> readGenre(final @PathVariable long movieId,
                                          final @PathVariable long genreId,
                                          final @RequestParam(required = false) String[] include) {
    Genre genre = movieService.readGenre(movieId, genreId);
    String result = this.writer("genreFilter", genre, include);
    if (result == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies/{movieId}/genres")
  public ResponseEntity<String> readGenre(final @PathVariable long movieId,
                                          final @RequestParam(required = false) String[] include) {
    Set<Genre> genres = movieService.readGenre(movieId);
    String result = this.writer("genreFilter", genres, include);
    if (result == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies/{movieId}/directors/{directorId}")
  public ResponseEntity<String> readDirector(final @PathVariable long movieId,
                                             final @PathVariable long directorId,
                                             final @RequestParam(required = false) String[] include) {
    Director director = movieService.readDirector(movieId, directorId);
    String result = this.writer("directorFilter", director, include);
    if (result == null) {
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies/{movieId}/directors")
  public ResponseEntity<String> readDirector(final @PathVariable long movieId,
                                             final @RequestParam(required = false) String[] include) {
    Set<Director> directors = movieService.readDirector(movieId);
    String result = this.writer("directorFilter", directors, include);
    if (result == null) {
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies/{movieId}/stars/{starId}")
  public ResponseEntity<String> readStar(final @PathVariable long movieId,
                                         final @PathVariable long starId,
                                         final @RequestParam(required = false) String[] include) {
    Star star = movieService.readStar(movieId, starId);
    String result = this.writer("starFilter", star, include);
    if (result == null) {
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/movies/{movieId}/stars")
  public ResponseEntity<String> readStar(final @PathVariable long movieId,
                                         final @RequestParam(required = false) String[] include) {
    Set<Star> stars = movieService.readStar(movieId);
    String result = this.writer("starFilter", stars, include);
    if (result == null) {
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }

  private String writer(final String filterName, final Object o, final String[] include) {
    FilterProvider provider;
    if (Objects.isNull(include)) {
      provider = MovieFilter.buildFilter(filterName, false, null);
    } else {
      provider = MovieFilter.buildFilter(filterName, true, include);
    }
    writer = mapper.writer(provider);
    return this.writeValueAsString(o);
  }

  private String writeValueAsString(final Object o) {
    try {
      return writer.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      logger.error("Unable write " + o.getClass().getTypeName() + " to JSON", e);
    }
    return null;
  }

}
