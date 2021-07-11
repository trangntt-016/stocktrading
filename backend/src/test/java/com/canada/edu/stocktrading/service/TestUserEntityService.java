package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserEntityService {
    @MockBean
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private UserEntityService userEntityService;

    @Test
    public void testIsUniqueEmail(){
        UserEntity userEntity = null;
        String userEmail = "test@gmail.com";

        Mockito.when(userEntityRepository.findByEmail(userEmail)).thenReturn(null);
        boolean result = userEntityService.isEmailUnique(userEmail);
        assertTrue(result);
    }
}
