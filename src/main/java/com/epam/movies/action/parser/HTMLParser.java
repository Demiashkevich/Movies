package com.epam.movies.action.parser;

import com.epam.movies.entity.Entity;

import java.util.List;

public abstract class HTMLParser<T extends Entity> {

    protected String url;

    public HTMLParser(String url) {
        this.url = url;
    }

    public abstract List<T> parse();

}
