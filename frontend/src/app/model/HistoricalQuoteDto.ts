
export interface ChartDto{
  name: string;
  series: HistoricalQuoteDto[];
}

export interface HistoricalQuoteDto{
  symbol: string;
  date: Date;
  close: number;
}
