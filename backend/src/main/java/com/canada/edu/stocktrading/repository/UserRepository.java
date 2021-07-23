package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserEntityRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.email =:email")
    List<User> findByEmail(String email);



}
