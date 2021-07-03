package com.canada.edu.stocktrading;

import com.canada.edu.stocktrading.service.StockAPIService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestStockAPIService {
    @Autowired
    private StockAPIService stockAPIService;

    @Test
    public void testGetThreePopularStocks() throws JSONException, IOException {

    }
}
