package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.client.controller.exception.DuplicateEmailException;
import com.canada.edu.stocktrading.client.controller.exception.InternalServerException;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.service.UserEntityService;
import com.canada.edu.stocktrading.service.dto.RegisteredUserDto;
import com.canada.edu.stocktrading.service.dto.UserEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;

    @PostMapping("")
    public ResponseEntity<UserEntityDto>register(@RequestBody RegisteredUserDto userEntity){
        if(!userEntityService.isEmailUnique(userEntity.getEmail())){
            throw new DuplicateEmailException("This email already exists!");
        }
        UserEntityDto newUsr = null;
        try{
            newUsr = userEntityService.save(userEntity);
        }
        catch(Exception ex){
            throw new InternalServerException("Cannot save user to the database!");
        }
        return new ResponseEntity<UserEntityDto>(newUsr,HttpStatus.ACCEPTED);
    }
}
