package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.client.controller.exception.BadRequestException;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.repository.SymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface SymbolService {
    List<Symbol> getAll();

    Symbol getOneBySymbolId(Integer symbolId);
}
