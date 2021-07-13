package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.Watchlist;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.repository.WatchlistRepository;

import com.canada.edu.stocktrading.utils.EntityUtils;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestWatchlistRepository {
    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private EntityUtils entityUtils;

    @Test
    public void testCreateNewWatchList(){
        UserEntity randomUsr = entityUtils.generateRandomUser();
        int before = watchlistRepository.findAll().size();
        Watchlist newWL = Watchlist.builder()
                .user(randomUsr)
                .name("test")
                .build();
        watchlistRepository.save(newWL);

        int after = watchlistRepository.findAll().size();
        assertThat(after).isEqualTo(before+1);
    }

    @Test
    public void testDeleteAWatchList(){
        Watchlist randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());

        int before = watchlistRepository.findAll().size();
        watchlistRepository.delete(randomWL);

        int after = watchlistRepository.findAll().size();
        assertThat(after).isEqualTo(before-1);
    }

    @Test
    public void testAddNewSymbolToWatchlist(){
        Symbol randomSymbol = entityUtils.generateRandomEntity(symbolRepository,symbolRepository.findAll().get(0).getSymbolId());
        Watchlist randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());

        randomWL.addSymbols(randomSymbol);
        int before = watchlistRepository.findById(randomWL.getWatchlistId()).get().getSymbols().size();
        watchlistRepository.save(randomWL);
        symbolRepository.findById(randomSymbol.getSymbolId()).get().getWatchlists();
        int after = watchlistRepository.findById(randomWL.getWatchlistId()).get().getSymbols().size();
        assertThat(after).isEqualTo(before+1);
    }

    @Test
    public void testDeleteASymbolFromWatchlist(){
        Watchlist randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());
        Symbol symbol = randomWL.getSymbols().stream().collect(Collectors.toList()).get(0);
        randomWL.getSymbols().remove(symbol);
        System.out.println(randomWL.getWatchlistId());
        watchlistRepository.save(randomWL);
    }

    @Test
    public void testFindAllWatchlistsFromUserId(){
        UserEntity randomUsr = entityUtils.generateRandomUser();
        watchlistRepository.findAllByUserId(randomUsr.getUserId());
    }

    @Test
    public void testUpdateAWatchlist(){
        Watchlist randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());
        int before = watchlistRepository.findAll().size();
        String testName = "sometest";
        randomWL.setName(testName);
        watchlistRepository.save(randomWL);
        int after = watchlistRepository.findAll().size();
        assertThat(before).isEqualTo(after);
        assertThat(watchlistRepository.findById(randomWL.getWatchlistId()).get().getName()).isEqualTo(testName);
    }


}
