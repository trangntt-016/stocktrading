package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.AuthenticationType;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.service.dto.RegisteredUserDto;
import com.canada.edu.stocktrading.service.dto.UserEntityDto;
import com.canada.edu.stocktrading.service.utils.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean isEmailUnique(String email){
        int noOfFound = userEntityRepository.findByEmail(email).size();
        return noOfFound==0;
    }

    public UserEntityDto save(RegisteredUserDto user){
        String encodedPassword = "";
        if(user.getAuthenticationType().equals("DATABASE")){
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
        return MapperUtils.mapperObject(savedUsr, UserEntityDto.class);
    }

}
