package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestUserEntityRepository {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserEntityUtils utils;

    @Test
    public void testFindByEmail(){
        // generate random user in the database
        UserEntity randomUsr = utils.generateRandomUser();

        int noOfFound = userEntityRepository.findByEmail(randomUsr.getEmail()).size();
        assertThat(noOfFound).isEqualTo(1);
    }

}

@Component
class UserEntityUtils{
    @Autowired
    private UserEntityRepository userEntityRepository;

    public  UserEntity generateRandomUser(){
        int randomNum = generateRandomNumber(0,userEntityRepository.findAll().size());
        List<UserEntity>users = userEntityRepository.findAll();
        return users.get(randomNum);
    }

    public static int generateRandomNumber(int min, int max){
        return (int)(Math.random()*(max-min)+min);
    }
}
