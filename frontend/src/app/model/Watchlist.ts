import {Symbol} from './Symbol';

export interface Watchlist{
  watchlistId: number;
  name: string;
  symbols: Symbol[];
}
