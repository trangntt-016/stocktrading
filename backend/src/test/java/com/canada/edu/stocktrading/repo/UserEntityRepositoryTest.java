package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.User;
import com.canada.edu.stocktrading.repository.UserRepository;
import com.canada.edu.stocktrading.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityUtils utils;

    @Test
    public void testFindByEmail(){
        User randomUsr = utils.generateRandomUser();

        Optional<User> found = userRepository.findByEmail(randomUsr.getEmail());

        assertThat(found.get()).isEqualTo(randomUsr);
    }

    @Test
    public void testFindByUserId(){
        User randomUsr = utils.generateRandomUser();

        User found = userRepository.findById(randomUsr.getUserId()).get();

        assertThat(found).isEqualTo(randomUsr);
    }

}

