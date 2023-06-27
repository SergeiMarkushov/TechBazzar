package ru.bazzar.user.converters;

import org.springframework.stereotype.Component;
import ru.bazzar.user.api.UserDto;
import ru.bazzar.user.entities.User;

@Component
public class UserConverter {

    //из user в dto
    public UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setBalance(user.getBalance());
        userDto.setActive(user.isActive());
        return userDto;
    }
    //из dto в user
    public User dtoToEntity(UserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .balance(userDto.getBalance())
                .build();
    }
}
