package com.canada.edu.stocktrading.service;

import com.canada.edu.stocktrading.dto.PositionDto;

public interface PositionService {
    PositionDto getAPosition(Integer symbolId, String userId);
}
