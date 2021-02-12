package ru.vyazankin.market.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserDto {
    private String name;
    private String password;
    private String email;
    private String error;
}
