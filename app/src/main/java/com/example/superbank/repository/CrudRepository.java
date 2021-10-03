package com.example.superbank.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    T add(T entity);

    T update(T entity, ID entityId);

    Optional<T> get(ID entityId);

    List<T> getAll();

    boolean existsById(ID entityId);

    void delete(ID entityId);


}
