package com.canada.edu.stocktrading.controller;

import com.canada.edu.stocktrading.api.SymbolApi;
import com.canada.edu.stocktrading.factory.ResponseFactory;
import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/symbol")
public class SymbolController implements SymbolApi {
    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    SymbolService symbolService;

    @GetMapping
    public ResponseEntity<?> getAllSymbols() {
        try {
            List<Symbol>symbols = symbolService.getAll();
            return responseFactory.success(symbols);
        }
        catch(Exception ex) {
            return responseFactory.internalServerError("Unable to get all symbols");
        }

    }
}

