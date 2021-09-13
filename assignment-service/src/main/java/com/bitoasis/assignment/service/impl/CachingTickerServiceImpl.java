package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.service.CachingTickerService;

import java.util.concurrent.TimeUnit;

public class CachingTickerServiceImpl implements CachingTickerService {

    private CachingRepository cachingRepository;
    private Integer cachingTickerTime;

    public CachingTickerServiceImpl(CachingRepository cachingRepository, Integer cachingTickerTime) {
        this.cachingRepository = cachingRepository;
        this.cachingTickerTime = cachingTickerTime;
    }

    @Override
    public void cachingTicker(String code, TickerBo tickerBo) {
        cachingRepository.save(code, tickerBo, cachingTickerTime, TimeUnit.MINUTES);
    }
}
