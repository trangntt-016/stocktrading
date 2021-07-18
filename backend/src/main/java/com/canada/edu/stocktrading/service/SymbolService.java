package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SymbolService {
    @Autowired
    SymbolRepository symbolRepository;

    public List<Symbol>findAllSymbols(){
        return symbolRepository.findAll();
    }

    public Symbol findBySymbolId(Integer symbolId) throws Exception {
        Optional<Symbol>found = symbolRepository.findById(symbolId);
        if(found.isEmpty()){
            throw new Exception("Unable to find a symbol with id "+symbolId);
        }
        return found.get();
    }
}
