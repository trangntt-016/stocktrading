package com.canada.edu.stocktrading.service;

import org.springframework.stereotype.Service;

@Service
public class StockService {
    //final private SymbolAPIService symbolAPIService = SymbolAPIService.getInstance();

//    final private StockAPIService stockAPIInstance = StockAPIService.getInstance();
//
//    public StockService() throws IOException { }
//
//    public List<ChartDto>getHistoricalQuotesForTopThreeCharts() throws IOException {
//        List<Stock>topThreeStocks = stockAPIInstance.topThreeStocks;
//        List<ChartDto>charts = new ArrayList<>();
//        for(Stock s: topThreeStocks){
//            List<HistoricalQuoteDto>list = MapperUtils.mapperList(s.getHistory(),HistoricalQuoteDto.class);
//            ChartDto chart = ChartDto.builder()
//                    .name(list.get(0).getSymbol())
//                    .series(list)
//                    .build();
//            charts.add(chart);
//        }
//        return charts;
//    }
//
//    public List<SummaryStockDto>getSummaryTopThreeStocks(){
//        List<Stock>topThreeStocks = stockAPIInstance.topThreeStocks;
//        List<SummaryStockDto>stocks = new ArrayList<SummaryStockDto>();
//        topThreeStocks.stream().forEach(stock->{
//            SummaryStockDto s = SummaryStockDto.builder()
//                    .symbol(stock.getSymbol())
//                    .currentPrice(stock.getQuote().getPrice())
//                    .change(stock.getQuote().getChange())
//                    .changePercent(stock.getQuote().getChangeInPercent())
//                    .build();
//            stocks.add(s);
//        });
//        return stocks;
//    }
//    public List<String>getSummaryTopThreeStocks(){
//        List<Stock>topThreeStocks = stockAPIInstance.topThreeStocks;
//        return topThreeStocks.stream().map(Stock::getSymbol).collect(Collectors.toList());
//    }
}
