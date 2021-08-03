package com.canada.edu.stocktrading.repo;


import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserEntityRepositoryTest {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private EntityUtils utils;

    @Test
    public void testFindByEmail(){
        UserEntity randomUsr = utils.generateRandomUser();

        Optional<UserEntity> found = userEntityRepository.findByEmail(randomUsr.getEmail());

        assertThat(found.get()).isEqualTo(randomUsr);
    }

    @Test
    public void testFindByUserId(){
        UserEntity randomUsr = utils.generateRandomUser();

        UserEntity found = userEntityRepository.findById(randomUsr.getUserId()).get();

        assertThat(found).isEqualTo(randomUsr);
    }

}

