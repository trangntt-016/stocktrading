package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.AuthenticationType;
import com.canada.edu.stocktrading.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    @Query("SELECT u FROM UserEntity u WHERE u.email =:email")
    List<UserEntity> findByEmail(String email);

    @Query("UPDATE UserEntity u SET u.authenticationType =:type")
    @Modifying
    void updateAuthenticationType(AuthenticationType type);
}
