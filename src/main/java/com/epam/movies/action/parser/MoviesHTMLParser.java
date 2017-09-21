package com.epam.movies.action.parser;

import com.epam.movies.action.timer.LocalDateTime;
import com.epam.movies.builder.MovieBuilder;
import com.epam.movies.entity.Entity;
import com.epam.movies.entity.Movie;
import com.epam.movies.manager.ResourceManager;
import org.joda.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesHTMLParser extends HTMLParser<Movie> {

    private static final String LOCAL_FORMAT = ResourceManager.getConfiguration("movie.local.time.format");
    private static final int LOCAL_TIME_YEAR_START = Integer.parseInt(ResourceManager.getConfiguration("movie.local.time.start.year"));
    private static final int LOCAL_TIME_MONTH_START = Integer.parseInt(ResourceManager.getConfiguration("movie.local.time.start.month"));
    private static final int LOCAL_TIME_YEAR_END = Integer.parseInt(ResourceManager.getConfiguration("movie.local.time.end.year"));
    private static final int LOCAL_TIME_MONTH_END = Integer.parseInt(ResourceManager.getConfiguration("movie.local.time.end.month"));
    private static final String TAG_CLASS_BLOCK_MOVIES = ResourceManager.getConfiguration("movie.tag.class.block.movies");
    private static final String TAG_CLASS_OVERVIEW = ResourceManager.getConfiguration("movie.tag.class.overview");
    private static final String TAG_NAME = ResourceManager.getConfiguration("movie.tag.name");
    private static final String TAG_CERT = ResourceManager.getConfiguration("movie.tag.cert");
    private static final String TAG_TIME = ResourceManager.getConfiguration("movie.tag.time");
    private static final String TAG_GENRE = ResourceManager.getConfiguration("movie.tag.genre");
    private static final String TAG_METASCOPE = ResourceManager.getConfiguration("movie.tag.metascore");
    private static final String TAG_DESCRIPTION = ResourceManager.getConfiguration("movie.tag.description");
    private static final String TAG_DIRECTORS_ATTRIBUTE_KEY = ResourceManager.getConfiguration("movie.tag.txt.block.directors.attribute.key");
    private static final String TAG_DIRECTORS_ATTRIBUTE_VALUE = ResourceManager.getConfiguration("movie.tag.txt.block.directors.attribute.value");
    private static final String TAG_STARS_ATTRIBUTE_KEY = ResourceManager.getConfiguration("movie.tag.txt.block.stars.attribute.key");
    private static final String TAG_STARS_ATTRIBUTE_VALUE = ResourceManager.getConfiguration("movie.tag.txt.block.stars.attribute.value");
    private static final String TAG_SOURCE_TITLE = ResourceManager.getConfiguration("movie.tag.source-to-get.title");
    private static final String TAG_SOURCE_CERT = ResourceManager.getConfiguration("movie.tag.source-to-get.cert");
    private static final String TAG_SOURCE_DATETIME = ResourceManager.getConfiguration("movie.tag.source-to-get.datetime");
    private static final String TAG_SOURCE_DIRECTOR = ResourceManager.getConfiguration("movie.tag.source-to-get.director");
    private static final String TAG_SOURCE_STAR = ResourceManager.getConfiguration("movie.tag.source-to-get.star");
    private static final String TAG_REMOVE_GHOST = ResourceManager.getConfiguration("movie.tag.remove.ghost");

    private static long count;

    public MoviesHTMLParser(final String url) {
        super(url);
    }

    public List<Movie> parse() {
        final List<Movie> results= new ArrayList<>();
        final LocalDate start = new LocalDate(LOCAL_TIME_YEAR_START, LOCAL_TIME_MONTH_START, 1);
        final LocalDate end = new LocalDate(LOCAL_TIME_YEAR_END, LOCAL_TIME_MONTH_END, 1);
        final SimpleDateFormat format = new SimpleDateFormat(LOCAL_FORMAT);
        for (final LocalDate dateTime : new LocalDateTime(start, end)) {
            List<Movie> moviePage = null;

            try {
                Thread.sleep(1000);
                final String currentUrl = url + format.format(dateTime.toDate());
                moviePage = this.parsePage(currentUrl);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (moviePage != null) {
                results.addAll(moviePage);
            }
        }
        return results;
    }

    private List<Movie> parsePage(final String urlPage) {
        final List<Movie> pageElements = new ArrayList<>();
        Document document;
        try {
            document = Jsoup.connect(urlPage).get();
            final Elements listItem = document.getElementsByClass(TAG_CLASS_BLOCK_MOVIES);
            for (final Element item : listItem) {
                final MovieBuilder movieBuilder = new MovieBuilder();

                final Elements overview = item.select(TAG_CLASS_OVERVIEW);

                movieBuilder.buildId(this.generateId());

                final Elements nameElements = overview.select(TAG_NAME);
                movieBuilder.buildTitle(this.parseTitle(nameElements));

                final Elements certRuntimeElements = overview.select(TAG_CERT);
                movieBuilder.buildCertRuntime(this.parseCertificateRuntime(certRuntimeElements));

                final Elements timeElements = overview.select(TAG_TIME);
                movieBuilder.buildTimeDate(this.parseDateTime(timeElements));

                final Elements genreElements = overview.select(TAG_GENRE);
                final List<String> genresString = this.parseGenre(genreElements);
                movieBuilder.buildGenres(genresString);

                final Elements metascoreElements = overview.select(TAG_METASCOPE);
                movieBuilder.buildMetascope(this.parseMetascore(metascoreElements));

                final Elements descriptionElements = overview.select(TAG_DESCRIPTION);
                movieBuilder.buildDescription(this.parseDescription(descriptionElements));

                final Elements txtBlockDirectorElements = overview.first().getElementsByAttributeValue(TAG_DIRECTORS_ATTRIBUTE_KEY, TAG_DIRECTORS_ATTRIBUTE_VALUE);
                List<String> directorsString = this.parseDirectors(txtBlockDirectorElements);
                movieBuilder.buildDirectors(directorsString);

                final Elements txtBlockStarElements = overview.first().getElementsByAttributeValue(TAG_STARS_ATTRIBUTE_KEY, TAG_STARS_ATTRIBUTE_VALUE);
                List<String> starsString = this.parseStars(txtBlockStarElements);
                movieBuilder.buildStars(starsString);

                pageElements.add(movieBuilder.getMovie());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageElements;
    }

    private long generateId() {
        return ++count;
    }

    private String parseTitle(final Elements nameElements) {
        String title = null;
        if (nameElements != null && !nameElements.isEmpty()) {
            title = nameElements.attr(TAG_SOURCE_TITLE);
        }
        return title;
    }

    private String parseCertificateRuntime(final Elements certRuntimeGenreElements){
        String certificateRuntime = null;
        if (certRuntimeGenreElements != null && !certRuntimeGenreElements.isEmpty()) {
            certificateRuntime = certRuntimeGenreElements.attr(TAG_SOURCE_CERT);
        }
        return certificateRuntime;
    }

    private String parseDateTime(final Elements timeElements) {
        String datetime = null;
        if (timeElements != null && !timeElements.isEmpty()) {
            datetime = timeElements.attr(TAG_SOURCE_DATETIME);
        }
        return datetime;
    }

    private List<String> parseDirectors(final Elements txtBlockDirectorElements) {
        final List<String> directors = new ArrayList<>();
        for (final Element txtBlockDirectorElement : txtBlockDirectorElements) {
            directors.add(txtBlockDirectorElement.select(TAG_SOURCE_DIRECTOR).first().ownText());
        }
        return directors;
    }

    private List<String> parseStars(final Elements txtBlockStarElements){
        final List<String> stars = new ArrayList<>();
        for (final Element txtBlockStarElement : txtBlockStarElements) {
            stars.add(txtBlockStarElement.select(TAG_SOURCE_STAR).first().ownText());
        }
        return stars;
    }

    private String parseDescription(final Elements descriptionElements){
        String description = null;
        if (descriptionElements != null && !descriptionElements.isEmpty()) {
            description = descriptionElements.first().ownText();
        }
        return description;
    }

    private List<String> parseGenre(final Elements genreElements) {
        List<String> genres = new ArrayList<>();
        if (genreElements != null && !genreElements.isEmpty()) {
            genres = genreElements.stream()
                    .filter(genre -> !genre.className().equals(TAG_REMOVE_GHOST))
                    .map(Element::ownText)
                    .collect(Collectors.toList());
        }
        return genres;
    }

    private String parseMetascore(final Elements metascoreElements) {
        String metascope = null;
        if (metascoreElements != null && !metascoreElements.isEmpty()) {
            metascope = metascoreElements.first().ownText();
        }
        return metascope;
    }

}
