package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.User;
import com.canada.edu.stocktrading.dto.UserRegisteredDto;
import com.canada.edu.stocktrading.dto.UserDto;

public interface UserService {
    UserDto save(UserRegisteredDto user);

    boolean isUserIdValid(String userId);

    User findByUserId(String userId);
}
