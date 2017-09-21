package com.epam.movies.service;

import com.epam.movies.entity.Entity;

import java.io.Serializable;
import java.util.Set;

public interface ItemService<T extends Entity<K>, K extends Serializable> {

  T read(K key);

  Set<T> read();

  Set<T> read(String...searchAttribute);

}
