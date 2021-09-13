package com.bitoasis.assignment.service;

import com.bitoasis.assignment.bo.TickerBo;

public interface CachingTickerService {

    void cachingTicker(String code, TickerBo tickerBo);
}
