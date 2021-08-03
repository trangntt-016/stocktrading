package com.canada.edu.stocktrading.repository;


import com.canada.edu.stocktrading.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    @Query("SELECT u FROM UserEntity u WHERE u.email =:email")
    Optional<UserEntity> findByEmail(String email);



}
