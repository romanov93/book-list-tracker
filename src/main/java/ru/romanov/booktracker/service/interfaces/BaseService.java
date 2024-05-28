package ru.romanov.booktracker.service.interfaces;

public interface BaseService<T> {

    T getById(Long id);

    T create(T t);

    T update(T t);

    void delete(Long id);
}
