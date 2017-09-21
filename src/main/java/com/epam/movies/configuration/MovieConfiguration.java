package com.epam.movies.configuration;

import com.epam.movies.action.parser.MoviesHTMLParser;
import com.epam.movies.manager.ResourceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieConfiguration {

  private static String url = ResourceManager.getConfiguration("url");

  @Bean
  public MoviesHTMLParser moviesHTMLParser() {
    return new MoviesHTMLParser(url);
  }

}
