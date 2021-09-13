package com.bitoasis.assignment.repository.impl;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.enums.CachingKey;
import com.bitoasis.assignment.repository.CachingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CachingRepositoryImpl implements CachingRepository {

    private RedisTemplate redisTemplate;

    @Autowired
    public CachingRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(CachingKey cachingKey, List<CoinBo> coins) {
        ValueOperations<String,List<CoinBo>> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(cachingKey.name(), coins);

    }

    @Override
    public List<CoinBo> findAll(CachingKey cachingKey) {
        ValueOperations<String,List<CoinBo>> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(cachingKey.name());
    }

    @Override
    public void save(String key, TickerBo tickerBo, Integer time, TimeUnit timeUnit) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, tickerBo, time, timeUnit);
    }

    @Override
    public TickerBo find(String code) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return (TickerBo) valueOperations.get(code);
    }
}
