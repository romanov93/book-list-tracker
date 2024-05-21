package ru.romanov.booktracker.web.mapper;

import org.mapstruct.Mapper;
import ru.romanov.booktracker.domain.user.User;
import ru.romanov.booktracker.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {

}
