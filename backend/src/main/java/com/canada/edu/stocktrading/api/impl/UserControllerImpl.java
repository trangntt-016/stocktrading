package com.canada.edu.stocktrading.api.impl;

import com.canada.edu.stocktrading.api.UserController;
import com.canada.edu.stocktrading.api.exception.BadRequestException;
import com.canada.edu.stocktrading.api.exception.DuplicateEmailException;
import com.canada.edu.stocktrading.api.exception.InternalServerException;
import com.canada.edu.stocktrading.dto.UserAuthRequestDto;
import com.canada.edu.stocktrading.dto.UserAuthResponseDto;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.service.impl.UserEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private UserEntityServiceImpl userService;



    @PostMapping("/register")
    public ResponseEntity<?>register(UserAuthRequestDto user) {
        try{
            UserAuthResponseDto newUsr  = userService.saveAUser(user);
            return this.responseFactory.created(newUsr);
        }
        catch(DuplicateEmailException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch(Exception ex){
            throw new InternalServerException("Cannot save user to the database!");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthRequestDto auth) {
        try {
            UserAuthResponseDto userDto = userService.login(auth);
            return this.responseFactory.success(userDto);
        } catch (BadRequestException e) {
            throw new BadCredentialsException("Email and Password do not matched!");
        } catch (Exception e) {
            throw new InternalServerException("Unable to login with email " + auth.getEmail());
        }
    }

}
