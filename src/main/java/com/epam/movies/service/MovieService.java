package com.epam.movies.service;

import com.epam.movies.action.parser.HTMLParser;
import com.epam.movies.action.parser.MoviesHTMLParser;
import com.epam.movies.entity.Director;
import com.epam.movies.entity.Genre;
import com.epam.movies.entity.Movie;
import com.epam.movies.entity.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@Service
public class MovieService extends AbstractService<Movie, Long> {

  @Autowired
  private HTMLParser<Movie> moviesHTMLParser;

  @PostConstruct
  public void init() {
    items = moviesHTMLParser.parse();
  }

  @Override
  public Set<Movie> read(final String... searchAttribute) {
    return null;
  }

  public Genre readGenre(final Long movieId, final Long genreId) {
    Movie movie = this.findMovie(movieId);
    return movie.getGenres().stream()
        .filter(g -> Objects.equals(genreId, g.getId()))
        .findFirst().get();
  }

  public Set<Genre> readGenre(final Long movieId) {
    Movie movie = this.findMovie(movieId);
    return new HashSet<>(movie.getGenres());
  }

  public Director readDirector(final Long movieId, final Long directorId) {
    Movie movie = this.findMovie(movieId);
    return movie.getDirectors().stream()
        .filter(d->Objects.equals(directorId, d.getId()))
        .findFirst().get();
  }

  public Set<Director> readDirector(final Long movieId) {
    Movie movie = this.findMovie(movieId);
    return new HashSet<>(movie.getDirectors());
  }

  public Star readStar(final Long movieId, final Long starId) {
    Movie movie = this.findMovie(movieId);
    return movie.getStars().stream()
        .filter(s->Objects.equals(starId, s.getId()))
        .findFirst().get();
  }

  public Set<Star> readStar(final Long movieId) {
    Movie movie = this.findMovie(movieId);
    return new HashSet<>(movie.getStars());
  }

  private Movie findMovie(final Long movieId) {
    return items.stream()
        .filter(m -> Objects.equals(movieId, m.getId()))
        .findFirst().get();
  }

}
