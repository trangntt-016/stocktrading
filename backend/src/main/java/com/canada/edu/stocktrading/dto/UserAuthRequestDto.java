package com.canada.edu.stocktrading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthRequestDto extends UserDto{
    String authenticationType;
    String password;
}