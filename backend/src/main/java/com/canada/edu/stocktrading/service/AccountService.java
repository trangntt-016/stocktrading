package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.model.Account;

public interface AccountService {
    Account getAccountByUserId(String userId);
}
