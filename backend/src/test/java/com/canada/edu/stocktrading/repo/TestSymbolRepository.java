package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSymbolRepository {
    @Autowired
    SymbolRepository symbolRepository;

    @Test
    public void testCreateNewSymbol(){
        Symbol symbol = Symbol.builder()
                .symbol("CSGP")
                .name("CoStar Group, Inc. Common Stock")
                .build();
        symbolRepository.save(symbol);
    }
}
