package com.epam.movies.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface MovieController {
  @ApiOperation("View the particular movie")
  ResponseEntity<String> readMovie(@PathVariable long var1, @RequestParam(required = false) String[] var3);

  @ApiOperation("View all movies")
  ResponseEntity<String> readMovie(@RequestParam(required = false) String[] var1);

  @ApiOperation("View the particular genre for the movie")
  ResponseEntity<String> readGenre(@PathVariable long var1, @PathVariable long var3, @RequestParam(required = false) String[] var5);

  @ApiOperation("View all genres for the movie")
  ResponseEntity<String> readGenre(@PathVariable long var1, @RequestParam(required = false) String[] var3);

  @ApiOperation("View the particular director for the movie")
  ResponseEntity<String> readDirector(@PathVariable long var1, @PathVariable long var3, @RequestParam(required = false) String[] var5);

  @ApiOperation("View all directors for the movie")
  ResponseEntity<String> readDirector(@PathVariable long var1, @RequestParam(required = false) String[] var3);

  @ApiOperation("View the particular star for the movie")
  ResponseEntity<String> readStar(@PathVariable long var1, @PathVariable long var3, @RequestParam(required = false) String[] var5);

  @ApiOperation("View all stars for the movie")
  ResponseEntity<String> readStar(@PathVariable long var1, @RequestParam(required = false) String[] var3);
}
