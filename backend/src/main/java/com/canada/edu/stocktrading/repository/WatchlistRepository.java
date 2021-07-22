package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchList, Integer> {
    @Query("SELECT w FROM WatchList w WHERE w.user.userId =:userId")
    List<WatchList> findAllByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM watchlist_symbol WHERE symbol_id =:symbolId", nativeQuery = true)
    void deleteSymbolFromWatchList(Integer symbolId);
}
