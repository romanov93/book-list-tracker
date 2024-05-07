package ru.romanov.booktracker.service.interfaces;

import org.springframework.stereotype.Service;
import ru.romanov.booktracker.domain.user.User;

@Service
public interface UserService extends BaseService<User> {

    User getByUsername(String username);

    boolean isBookOwner(Long userId, Long bookId);


}
