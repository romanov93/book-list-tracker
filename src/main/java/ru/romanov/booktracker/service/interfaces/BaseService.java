package ru.romanov.booktracker.service.interfaces;

public interface BaseService<T> {

    T findById(Long id);

    T create(T t);

    T update(T t);

    void delete(Long id);
}
