package com.canada.edu.stocktrading.service.utils;

import yahoofinance.Stock;

import java.util.List;

public class QuickSortUtils {
    public enum SortType{
        ASCENDING,
        DESCENDING
    }
    public static void quicksort(List<Stock>stocks,int low, int high, SortType sortType){
        if(low<high){
            int pivot = partition(stocks, 0, high, sortType);
            quicksort(stocks,low,pivot-1, sortType);
            quicksort(stocks,pivot+1,high, sortType);
        }

    }

    public static int partition(List<Stock>stocks, int low, int high, SortType sortType){
        // compared value based on SortType
        int comparedVal = (sortType.compareTo(SortType.DESCENDING)==0)?1:-1;
        int i = low-1;
        int pivot = high;
        for(int j = low; j <=high-1; j++){
            if(stocks.get(j).getQuote().getPreviousClose().compareTo(stocks.get(pivot).getQuote().getPreviousClose())==comparedVal){
                i++;
                swap(stocks,i, j);
            }
        }
        swap(stocks,i+1, high);
        return i+1;
    }

    public static void swap(List<Stock>stocks,int i, int j ){
        Stock temp = stocks.get(i);
        stocks.set(i,stocks.get(j));
        stocks.set(j,temp);
    }
}
