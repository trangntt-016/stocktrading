package com.canada.edu.stocktrading.service;


import com.canada.edu.stocktrading.model.AuthenticationType;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.UserEntity;
import com.canada.edu.stocktrading.model.WatchList;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.repository.WatchlistRepository;
import com.canada.edu.stocktrading.dto.WatchListDto;
import com.canada.edu.stocktrading.service.impl.UserEntityServiceImpl;
import com.canada.edu.stocktrading.service.impl.WatchListServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class WatchListServiceImplTest {
    private static final String USER_ID = "1001";

    private static final String WATCH_LIST_NAME = "test";

    @InjectMocks
    private WatchListServiceImpl watchlistService;

    @Mock
    private UserEntityServiceImpl userService;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Test
    public void whenCreateWatchList_byValidUserId_thenReturnData() throws IOException {
        UserEntity user = buildUser();
        Optional<UserEntity> optUser = Optional.of(user);
        when(userEntityRepository.findById(USER_ID)).thenReturn(optUser);

        WatchList watchList = buildWatchList();
        when(watchlistRepository.save(any(WatchList.class))).thenReturn(watchList);

        WatchListDto expectedWatchListDto = buildWatchListDto();
        WatchListDto executedWatchListDto = watchlistService.createAWatchlist(USER_ID, WATCH_LIST_NAME);
        assertEquals(executedWatchListDto.getName(), expectedWatchListDto.getName());
    }

    @Test
    public void whenCreateWatchList_byInValidUserId_thenThrowException() {
        when(userEntityRepository.findById(USER_ID)).thenReturn(Optional.empty());
        try {
            WatchListDto executedWatchListDto = watchlistService.createAWatchlist(USER_ID, WATCH_LIST_NAME);
        } catch (Exception e) {
            System.out.println("==> Error: " + e.getMessage());
        }
    }

    @Test
    public void whenCreateWatchList_byUserId_thenThrowException() {
        UserEntity user = buildUser();
        try {
            when(userEntityRepository.findById(user.getUserId())).thenThrow(new Exception("Error during connect to database"));
            WatchListDto executedWatchListDto = watchlistService.createAWatchlist(USER_ID, WATCH_LIST_NAME);
        } catch (Exception e) {
            System.out.println("===> Error: " + e.getMessage());
        }
    }

    /**
     * PRIVATE METHODs block
     */

    private UserEntity buildUser() {
        return UserEntity.builder()
                .email("user@gmail.com")
                .authenticationType(AuthenticationType.DATABASE)
                .userId("1001")
                .password("abc123456")
                .build();
    }

    private HashSet<Symbol> buildSetSymbol() {
        Symbol symbol = Symbol.builder().symbolId(1).symbol("PYPL").name("PayPal Holdings, Inc. Common Stock").build();
        HashSet<Symbol> symbolSet = new HashSet<>();
        symbolSet.add(symbol);
        return symbolSet;
    }

    private WatchList buildWatchList() {
        HashSet<Symbol> symbolSet = buildSetSymbol();
        return WatchList.builder()
                .watchlistId(1)
                .name("test")
                .symbols(symbolSet)
                .build();
    }

    private WatchListDto buildWatchListDto() {
        return WatchListDto.builder()
                .watchlistId(10)
                .name("test")
                .build();
    }


}