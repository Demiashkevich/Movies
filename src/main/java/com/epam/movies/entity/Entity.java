package com.epam.movies.entity;

import java.io.Serializable;

public class Entity<K> implements Serializable {

  private K id;

  public K getId() {
    return id;
  }

  public void setId(K id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Entity entity = (Entity) o;

    return id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

}
