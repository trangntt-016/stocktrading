package com.canada.edu.stocktrading.repository;

import com.canada.edu.stocktrading.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SymbolRepository  extends JpaRepository<Symbol, Integer>{
    @Query("SELECT s.symbolId FROM Symbol s WHERE s.symbol =:symbol")
    Integer findSymbolIdBySymbol(String symbol);
}
