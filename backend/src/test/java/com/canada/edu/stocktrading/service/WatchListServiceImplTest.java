package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.AuthenticationType;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.model.User;
import com.canada.edu.stocktrading.model.WatchList;
import com.canada.edu.stocktrading.repository.UserEntityRepository;
import com.canada.edu.stocktrading.repository.WatchlistRepository;
import com.canada.edu.stocktrading.service.dto.WatchListDto;
import com.canada.edu.stocktrading.service.impl.UserServiceImpl;
import com.canada.edu.stocktrading.service.impl.WatchListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class WatchListServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @InjectMocks
    private WatchListServiceImpl watchlistService;

    @InjectMocks
    private UserServiceImpl userEntityService;

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Test
    public void create() {
        Symbol symbol = Symbol.builder().symbolId(1).symbol("PYPL").name("PayPal Holdings, Inc. Common Stock").build();
        User user = User.builder()
                .email("user@gmail.com")
                .authenticationType(AuthenticationType.DATABASE)
                .userId("U_003")
                .password("abc123456")
                .build();
        WatchListDto watchlistDto = WatchListDto.builder()
                .watchlistId(null)
                .name("testWatchlist")
                .symbols(null)
                .build();
        WatchList watchlist = WatchList.builder()
                .watchlistId(1)
                .name("testWatchlist")
                .symbols(Set.of(symbol))
                .build();


        when(userEntityRepository.findById(user.getUserId())).thenReturn(java.util.Optional.ofNullable(user));
        when(userEntityService.findByUserId(user.getUserId())).thenReturn(user);
        when(watchlistRepository.save(any(WatchList.class))).thenReturn(watchlist);

        WatchListDto dto = watchlistService.create("U_003","testWatchlist");
        assertThat(dto).isEqualTo(watchlistDto);
    }


}