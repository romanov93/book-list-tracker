package ru.romanov.booktracker.repository.interfaces;

import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);

    void update(T t);

    void create(T t);

    void delete(Long id);
}
