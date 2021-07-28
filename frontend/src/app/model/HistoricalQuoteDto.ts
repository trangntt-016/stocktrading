export interface ChartDto{
  name: string;
  series: HistoricalQuoteDto[];
}

export interface HistoricalQuoteDto{
  date: Date;
  open: number;
  close: number;
  high: number;
  low: number;
}
