package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    @Query("SELECT w FROM Watchlist w WHERE w.user.userId =:userId")
    public List<Watchlist>findAllByUserId(String userId);

}
