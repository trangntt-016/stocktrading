package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.api.exception.DuplicateEmailException;
import com.canada.edu.stocktrading.model.AuthenticationType;
import com.canada.edu.stocktrading.model.User;
import com.canada.edu.stocktrading.repository.UserRepository;
import com.canada.edu.stocktrading.service.UserService;
import com.canada.edu.stocktrading.dto.UserRegisteredDto;
import com.canada.edu.stocktrading.dto.UserDto;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDto save(UserRegisteredDto user) {
        if(!isEmailUnique(user.getEmail())) {
            throw new DuplicateEmailException("Email " + user.getEmail() + " has already existed in the database.");
        }

        String encodedPassword = "";
        if(user.getAuthenticationType().equals("DATABASE")){
            encodedPassword = passwordEncoder.encode((user.getPassword()));
        }
        AuthenticationType type = user.getAuthenticationType().equals("GOOGLE")
                ?AuthenticationType.GOOGLE
                :(user.getAuthenticationType().equals("FACEBOOK")
                ?AuthenticationType.FACEBOOK
                :AuthenticationType.DATABASE);
        User savedUsr = User.builder()
                .email(user.getEmail())
                .password(encodedPassword)
                .authenticationType(type)
                .build();
        userRepository.save(savedUsr);
        return MapperUtils.mapperObject(savedUsr, UserDto.class);
    }

    public boolean isUserIdValid(String userId){
        Optional<User> found = userRepository.findById(userId);
        return found.isPresent();
    }

    public User getUserByUserId(String userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new IllegalArgumentException("Unable to find user with id "+ userId);
        }
        return user.get();
    }

    public User getUserByUserEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new IllegalArgumentException("Unable to find user with id "+ email);
        }
        return user.get();
    }

    private boolean isEmailUnique(String email) {
        Optional<User> found = userRepository.findByEmail(email);

        if(found.isPresent()) {
            return false;
        }

        return true;
    }

}
