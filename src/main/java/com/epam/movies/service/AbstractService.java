package com.epam.movies.service;

import com.epam.movies.action.parser.HTMLParser;
import com.epam.movies.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractService<T extends Entity<K>, K extends Serializable> implements ItemService<T, K> {

  protected List<T> items;

  @Override
  public T read(final K key) {
    if(Objects.isNull(key) && Objects.isNull(items)){
      return null;
    }
    return items.stream().filter(i -> key == i.getId()).findFirst().get();
  }

  @Override
  public Set<T> read() {
    if(Objects.isNull(items)){
      return null;
    }
    return new HashSet<>(items);
  }

  @Override
  public abstract Set<T> read(final String...searchAttribute);

}
