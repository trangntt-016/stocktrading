package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SymbolServiceIml implements SymbolService {
    @Autowired
    SymbolRepository symbolRepository;

    public List<Symbol> getAll() {
        return symbolRepository.findAll();
    }

    public Symbol getOneBySymbolId(Integer symbolId) {
        Optional<Symbol> found = symbolRepository.findById(symbolId);

        if(found.isEmpty()){
            throw new IllegalArgumentException("Unable to find a symbol with id "+symbolId);
        }

        return found.get();
    }
}
