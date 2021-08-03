package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.UserAuthRequestDto;
import com.canada.edu.stocktrading.dto.UserAuthResponseDto;
import com.canada.edu.stocktrading.model.UserEntity;

public interface UserEntityService {
    UserAuthResponseDto save (UserAuthRequestDto user);

    boolean isUserIdValid (String userId);

    UserEntity getUserByUserId (String userId);

    UserEntity getUserByUserEmail (String email);

    UserAuthResponseDto login (UserAuthRequestDto user);
}
