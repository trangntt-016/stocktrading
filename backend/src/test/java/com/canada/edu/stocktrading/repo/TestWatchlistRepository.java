package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.User;
import com.canada.edu.stocktrading.model.WatchList;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.repository.WatchlistRepository;

import com.canada.edu.stocktrading.utils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        User randomUsr = entityUtils.generateRandomUser();
        int before = watchlistRepository.findAll().size();
        WatchList newWL = WatchList.builder()
                .user (randomUsr)
                .name ("test")
                .build();
        watchlistRepository.save (newWL);

        int after = watchlistRepository.findAll().size();
        assertThat (after).isEqualTo (before+1);
    }

    @Test
    public void testDeleteAWatchList(){
        WatchList randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());

        int before = watchlistRepository.findAll().size();
        watchlistRepository.delete(randomWL);

        int after = watchlistRepository.findAll().size();
        assertThat(after).isEqualTo(before-1);
    }

    @Test
    public void testAddNewSymbolToWatchlist(){
        Symbol randomSymbol = entityUtils.generateRandomEntity(symbolRepository,symbolRepository.findAll().get(0).getSymbolId());

        WatchList randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());

        randomWL.addSymbols(randomSymbol);

        int before = watchlistRepository.findById(randomWL.getWatchlistId()).get().getSymbols().size();

        watchlistRepository.save(randomWL);

        symbolRepository.findById(randomSymbol.getSymbolId()).get().getWatchlists();

        int after = watchlistRepository.findById(randomWL.getWatchlistId()).get().getSymbols().size();

        assertThat(after).isEqualTo(before+1);
    }

    @Test
    public void testDeleteASymbolByWatchlistId(){
        WatchList randomWL = entityUtils.generateRandomEntity(watchlistRepository,watchlistRepository.findAll().get(0).getWatchlistId());

        Symbol symbol = randomWL.getSymbols().stream().collect(Collectors.toList()).get(0);

        watchlistRepository.deleteSymbolFromWatchList(symbol.getSymbolId());
    }

    @Test
    public void testFindAllWatchListsFromUserId(){
        User randomUsr = entityUtils.generateRandomUser();

        watchlistRepository.findAllByUserId(randomUsr.getUserId());
    }


}
