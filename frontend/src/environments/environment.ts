// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

// export const environment = {
//   production: false,
//   watchlistAPI: 'http://ec2-18-220-245-72.us-east-2.compute.amazonaws.com:3000/watchlist',
//   symbolAPI: 'http://ec2-18-220-245-72.us-east-2.compute.amazonaws.com:3000/symbol',
//   userAPI: 'http://ec2-18-220-245-72.us-east-2.compute.amazonaws.com:3000/user',
//   yahooAPI: 'http://ec2-18-220-245-72.us-east-2.compute.amazonaws.com:3000/yahoo-finance',
//   orderAPI: 'http://ec2-18-220-245-72.us-east-2.compute.amazonaws.com:3000/order',
//   stockWS: 'http://ec2-18-220-245-72.us-east-2.compute.amazonaws.com:3000/ws'
// };

export const environment = {
  production: false,
  watchlistAPI: 'http://localhost:3000/watchlist',
  symbolAPI: 'http://localhost:3000/symbol',
  userAPI: 'http://localhost:3000/user',
  yahooAPI: 'http://localhost:3000/yahoo-finance',
  orderAPI: 'http://localhost:3000/order',
  stockWS: 'http://localhost:3000/ws'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
