package com.canada.edu.stocktrading.client.controller;

import com.canada.edu.stocktrading.service.SymbolAPIService;
import com.canada.edu.stocktrading.service.dto.SymbolDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/symbols")
public class SymbolController {
    public SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();

    public SymbolController() throws IOException {
    }

    @GetMapping()
    public ResponseEntity<List<SymbolDto>> getAllSymbols(){
        List<SymbolDto>symbolsName = symbolAPIService.symbols;
        return ResponseEntity.ok(symbolsName);
    }
}

