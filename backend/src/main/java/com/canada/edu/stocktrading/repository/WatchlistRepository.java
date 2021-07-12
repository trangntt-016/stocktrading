package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
}
