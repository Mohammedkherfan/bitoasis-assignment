package com.bitoasis.assignment.manager;

import com.bitoasis.assignment.dto.CoinDto;
import com.bitoasis.assignment.enums.Sort;
import com.bitoasis.assignment.response.CoinTickerResponse;

import java.util.List;

public interface CoinManager {

    void startCaching();

    List<CoinDto> findAll(Sort sorting);

    CoinTickerResponse findTicker(String coin_code);
}
