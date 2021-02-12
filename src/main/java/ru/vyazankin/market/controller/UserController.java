package ru.vyazankin.market.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vyazankin.market.dto.UserDto;
import ru.vyazankin.market.service.UserService;

@RestController
@Slf4j
@RequestMapping("/openapi/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto){

        //Если пользователь существует
        if (userService.findByUsername(userDto.getName()).isPresent()){
            userDto.setError("Пользователь с указанным именем уже существует!");
            return userDto;
        }

        if (userService.findByEmail(userDto.getEmail()).isPresent()){
            userDto.setError("Пользователь с указанным почтовым адресом существует!");
            return userDto;
        }

        //Иначе сохраняем пользователя
        return userService.saveUserByUserDto(userDto);
    }
}
