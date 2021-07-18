package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.model.Symbol;
import com.canada.edu.stocktrading.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/symbols")
public class SymbolController {
    @Autowired
    SymbolService symbolService;
    //public SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();

//    public SymbolController() throws IOException {
//    }
//
    @GetMapping()
    public ResponseEntity<List<Symbol>> getAllSymbols(){
        List<Symbol>symbols = symbolService.findAllSymbols();
        return ResponseEntity.ok(symbols);
    }
}

