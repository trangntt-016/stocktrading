package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.api.exception.BadRequestException;
import com.canada.edu.stocktrading.api.exception.DuplicateEmailException;
import com.canada.edu.stocktrading.dto.UserAuthRequestDto;
import com.canada.edu.stocktrading.dto.UserAuthResponseDto;
import com.canada.edu.stocktrading.model.AuthenticationType;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.security.TokenProvider;
import com.canada.edu.stocktrading.service.UserEntityService;
import com.canada.edu.stocktrading.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private WatchListServiceImpl watchListService;

    @Autowired
    private TokenProvider tokenProvider;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserAuthResponseDto saveAUser(UserAuthRequestDto user) {
        // log in by Google, Facebook and already has email in the database
        if(!user.getAuthenticationType().equals("DATABASE") && !isEmailUnique(user.getEmail())) {
            Optional<UserEntity> userEntity = userEntityRepository.findByEmail(user.getEmail());
            return getUserAuthResponse(userEntity.get());
        }
        // sign up by database
        String encodedPassword = null;
        if(user.getAuthenticationType().equals("DATABASE")) {
            if (!isEmailUnique(user.getEmail())) {
                throw new DuplicateEmailException("Email " + user.getEmail() + " has already existed in the database.");
            }
            encodedPassword = passwordEncoder.encode((user.getPassword()));
        }
        AuthenticationType type = user.getAuthenticationType().equals("GOOGLE")
                ?AuthenticationType.GOOGLE
                :(user.getAuthenticationType().equals("FACEBOOK")
                ?AuthenticationType.FACEBOOK
                :AuthenticationType.DATABASE);

        UserEntity savedUsr = UserEntity.builder()
                .email(user.getEmail())
                .password(encodedPassword)
                .authenticationType(type)
                .build();

        userEntityRepository.save(savedUsr);

        watchListService.createDefaultWatchlist(savedUsr);

        UserAuthResponseDto userLoggedIn = getUserAuthResponse(savedUsr);

        return userLoggedIn;
    }

    public boolean isUserIdValid(String userId){
        Optional<UserEntity> found = userEntityRepository.findById(userId);
        return found.isPresent();
    }

    public UserEntity getUserByUserId(String userId){
        Optional<UserEntity> user = userEntityRepository.findById(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException("Unable to find user with id "+ userId);
        }
        return user.get();
    }

    @Override
    public UserAuthResponseDto login(UserAuthRequestDto userLogInDto) {
        if(isEmailUnique(userLogInDto.getEmail())) {
            throw new BadRequestException("Email " + userLogInDto.getEmail() + " hasn't been created.");
        }

        Optional<UserEntity> user = userEntityRepository.findByEmail(userLogInDto.getEmail());

        if(!isPasswordEmailMatched(user.get().getPassword(), userLogInDto.getPassword())){
            throw new BadRequestException("Email and password don't match.");
        }

        UserAuthResponseDto userLoggedIn = getUserAuthResponse(user.get());

        return userLoggedIn;
    }

    private boolean isEmailUnique(String email) {
        Optional<UserEntity> found = userEntityRepository.findByEmail(email);

        if(found.isPresent()) {
            return false;
        }
        return true;
    }

    private boolean isPasswordEmailMatched(String encodedPassword, String rawPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private UserAuthResponseDto getUserAuthResponse (UserEntity user) {
        final String jwt = tokenProvider.createToken(user.getEmail(), user.getUserId());

        UserAuthResponseDto userDto = MapperUtils.mapperObject(user, UserAuthResponseDto.class);

        userDto.setJwt(jwt);

        return userDto;
    }
}
