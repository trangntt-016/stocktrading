package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.UserAuthRequestDto;
import com.canada.edu.stocktrading.dto.UserAuthResponseDto;
import com.canada.edu.stocktrading.model.UserEntity;

public interface UserEntityService {
    UserAuthResponseDto saveAUser (UserAuthRequestDto user);

    boolean isUserIdValid (String userId);

    UserEntity getUserByUserId (String userId);

    UserAuthResponseDto login (UserAuthRequestDto user);
}
