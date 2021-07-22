package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.client.api.UserApi;
import com.canada.edu.stocktrading.client.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.client.controller.exception.DuplicateEmailException;
import com.canada.edu.stocktrading.client.controller.exception.InternalServerException;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.service.impl.UserServiceImpl;
import com.canada.edu.stocktrading.service.dto.UserRegisteredDto;
import com.canada.edu.stocktrading.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController implements UserApi {
    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<?>register(UserRegisteredDto userEntity) {
        try{
            UserDto newUsr  = userService.save(userEntity);
            return this.responseFactory.created(newUsr);
        }
        catch(DuplicateEmailException ex){
            throw new BadRequestException(ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException("Cannot save user to the database!");
        }
    }
}
