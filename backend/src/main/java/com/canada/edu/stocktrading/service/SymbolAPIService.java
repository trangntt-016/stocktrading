package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.service.dto.SymbolDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SymbolAPIService {
//    static private SymbolAPIService instance;
//
//
//    public List<SymbolDto>symbols = new ArrayList<>();
//
//    private SymbolAPIService() throws IOException {
//        // source file: https://api.nasdaq.com/api/screener/stocks?tableonly=true&limit=2&exchange=NASDAQ
//        // NASDAQ prohibits web scraping
//        File data = new File("src/main/java/com/canada/edu/stocktrading/service/utils/rawData.txt");
//        BufferedReader br = new BufferedReader(new FileReader(data));
//
//        // read file line by line
//        String st;
//        StringBuilder sb = new StringBuilder();
//        while ((st = br.readLine()) != null){
//            sb.append(st);
//        };
//        String raw = sb.toString();
//
//        // convert string to json object
//        JsonObject jsonObject = new JsonParser().parse(raw).getAsJsonObject();
//        // extract a row of stocks
//        JsonElement jStocks = null;
//        JsonArray jArray = null;
//        try{
//            jStocks = jsonObject.get("data").getAsJsonObject().get("table").getAsJsonObject().get("rows");
//            jArray = jStocks.getAsJsonArray();
//        }
//        catch (Exception ex){
//            throw new IllegalStateException("Cannot convert from string to an array of symbols.");
//        }
//        // extract symbols from stocks and add to list
//        for(JsonElement s: jArray){
//            SymbolDto sn = SymbolDto.builder()
//                    .symbol(s.getAsJsonObject().get("symbol").getAsString())
//                    .name(s.getAsJsonObject().get("name").getAsString())
//                    .build();
//            symbols.add(sn);
//        }
//    }
//
//    static public SymbolAPIService getInstance()throws IOException{
//        if(instance==null){
//            try{
//                instance = new SymbolAPIService();
//            }
//            catch(IOException e){
//                throw new IOException("File cannot be found!");
//            }
//            catch(IllegalStateException ex){
//                throw new IllegalStateException(ex.getMessage());
//            }
//        }
//        return instance;
//    }

}
