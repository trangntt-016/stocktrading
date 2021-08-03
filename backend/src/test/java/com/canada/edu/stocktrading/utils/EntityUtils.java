package com.canada.edu.stocktrading.utils;


import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityUtils {
    @Autowired
    private UserEntityRepository userEntityRepository;

    // because UserEntity doesn't have a primary key of Integer type
    public UserEntity generateRandomUser() {
        int randomNum = generateRandomNumber(0, userEntityRepository.findAll().size());
        List<UserEntity> users = userEntityRepository.findAll();
        return users.get(randomNum);
    }

    public <T> T generateRandomEntity(JpaRepository<T, Integer> repo, Integer first){
        // please check first index in the database, using generics prohibit from getting the first index in entity
        int last = repo.findAll().size();
        T object = null;
        Boolean found = false;
        while(!found){
            int randomNumber = generateRandomNumber(first, first+last);
            try {
                object = repo.findById(randomNumber).orElseThrow(()->new NotFoundException("Found nothing with this id"));
                if(object!=null){
                    found = true;
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return object;
    }

    public static int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
