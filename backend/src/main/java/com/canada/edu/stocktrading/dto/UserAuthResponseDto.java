package com.canada.edu.stocktrading.dto;

import lombok.Data;

@Data
public class UserLogInDto extends UserDto{
    String jwt;
}
