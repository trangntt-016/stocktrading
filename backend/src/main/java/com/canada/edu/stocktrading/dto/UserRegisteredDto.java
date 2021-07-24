package com.canada.edu.stocktrading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredDto extends UserDto{
    String authenticationType;
    String password;
}