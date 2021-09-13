package com.bitoasis.assignment.repository;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.enums.CachingKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

public class CachingRepositoryTest implements CachingRepository {

    private Map<String, List<CoinBo>> coins = new HashMap<>();
    private Map<String, TickerBo> tickers = new HashMap<>();


    @Override
    public void save(CachingKey cachingKey, List<CoinBo> coin) {
        List<CoinBo> list = coins.get(cachingKey.name());
        if (isNull(list))
            list = new ArrayList<>();
        list.addAll(coin);
        coins.put(cachingKey.name(), list);
    }

    @Override
    public List<CoinBo> findAll(CachingKey cachingKey) {
        List<CoinBo> list = coins.get(cachingKey.name());
        return list;
    }

    @Override
    public void save(String key, TickerBo tickerBo, Integer time, TimeUnit timeUnit) {
        tickers.put(key, tickerBo);
    }

    @Override
    public TickerBo find(String code) {
        return tickers.get(code);
    }

}
