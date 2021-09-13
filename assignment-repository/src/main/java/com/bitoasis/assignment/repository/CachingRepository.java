package com.bitoasis.assignment.repository;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.enums.CachingKey;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface CachingRepository {

    void save(CachingKey cachingKey, List<CoinBo> coins);

    List<CoinBo> findAll(CachingKey cachingKey);

    void save(String key, TickerBo tickerBo, Integer time, TimeUnit timeUnit);

    TickerBo find(String code);
}
