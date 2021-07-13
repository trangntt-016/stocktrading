package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.service.UserEntityService;
import com.canada.edu.stocktrading.utils.EntityUtils;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestUserEntityRepository {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private EntityUtils utils;

    @Test
    public void testFindByEmail(){
        UserEntity randomUsr = utils.generateRandomUser();

        int noOfFound = userEntityRepository.findByEmail(randomUsr.getEmail()).size();
        assertThat(noOfFound).isEqualTo(1);
    }

    @Test
    public void testFindByUserId(){
        UserEntity randomUsr = utils.generateRandomUser();

        UserEntity found = userEntityRepository.findById(randomUsr.getUserId()).get();
        assertThat(found).isEqualTo(randomUsr);
    }

}

