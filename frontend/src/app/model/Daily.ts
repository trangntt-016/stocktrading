import {Symbol} from './Symbol';

export interface Daily{
  dailyId: number;
  timestamp: Date;
  open: number;
  high: number;
  low: number;
  close: number;
  volume: number;
  bid: number;
  ask: number;
  prevClose: number;
  symbol: Symbol;
  change: number;
  changeInPercent: number
}

