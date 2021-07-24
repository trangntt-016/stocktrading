package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Symbol;

import java.util.List;


public interface SymbolService {
    List<Symbol> getAll();

    Symbol getOneBySymbolId(Integer symbolId);
}
