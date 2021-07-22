package com.canada.edu.stocktrading.repo;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.utils.EntityUtils;
import com.canada.edu.stocktrading.utils.NumberStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SymbolRepositoryTest {
    @Autowired
    SymbolRepository symbolRepository;

    @Autowired
    EntityUtils entityUtils;

    @Test
    public void testCreateNewSymbol(){
        Symbol randomSymbol = entityUtils.generateRandomEntity(symbolRepository, symbolRepository.findAll().get(0).getSymbolId());
        randomSymbol.setSymbolId(symbolRepository.findAll().size()+1);
        randomSymbol.setSymbol(NumberStringUtils.generateRandomString(4,false,true,false,false));

        int before = symbolRepository.findAll().size();

        randomSymbol = symbolRepository.save(randomSymbol);

        int after = symbolRepository.findAll().size();

        assertThat(after).isEqualTo(before + 1);

        symbolRepository.delete(randomSymbol);
    }
}
