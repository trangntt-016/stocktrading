package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Symbol;

import java.util.List;
import java.util.Set;


public interface SymbolService {
    List<Symbol> getAll();

    Symbol getOneBySymbolId(Integer symbolId);

    boolean isSymbolValid(Integer symbolId);

    Set<Symbol> getRandom05Symbols();
}
