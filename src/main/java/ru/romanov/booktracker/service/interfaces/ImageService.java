package ru.romanov.booktracker.service.interfaces;

import ru.romanov.booktracker.domain.book.BookImage;

public interface ImageService {

    String uploadImage(BookImage image);

}
