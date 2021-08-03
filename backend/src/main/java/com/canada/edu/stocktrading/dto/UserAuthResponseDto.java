package com.canada.edu.stocktrading.dto;

import lombok.Data;

@Data
public class UserAuthResponseDto extends UserDto{
    String jwt;
}
