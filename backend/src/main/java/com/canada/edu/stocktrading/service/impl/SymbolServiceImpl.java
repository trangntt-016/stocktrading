package com.canada.edu.stocktrading.service.impl;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import com.canada.edu.stocktrading.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SymbolServiceImpl implements SymbolService {
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

    @Override
    public boolean isSymbolValid(Integer symbolId) {
        try{
            getOneBySymbolId(symbolId);
            return true;
        }
        catch(IllegalArgumentException ex){
            return false;
        }
    }

    @Override
    public Set<Symbol> getRandom05Symbols() {
        Set<Symbol> randomSymbols = new HashSet<>();

        int max = getAll().size();

        int min = 1;

        while(randomSymbols.size() < 5) {
            int random = (int) (Math.random()*(max-min)+min);
            Symbol randomSymbol = symbolRepository.findById(random).get();
            randomSymbols.add(randomSymbol);
        }

        return randomSymbols;
    }
}
