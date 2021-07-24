package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.api.UserApi;
import com.canada.edu.stocktrading.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.controller.exception.DuplicateEmailException;
import com.canada.edu.stocktrading.controller.exception.InternalServerException;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.service.impl.UserServiceImpl;
import com.canada.edu.stocktrading.dto.UserRegisteredDto;
import com.canada.edu.stocktrading.dto.UserDto;
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
