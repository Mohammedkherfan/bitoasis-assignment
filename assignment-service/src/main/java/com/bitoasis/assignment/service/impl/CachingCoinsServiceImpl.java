package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.enums.CachingKey;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.service.CachingCoinsService;

import java.util.List;

public class CachingCoinsServiceImpl implements CachingCoinsService {

    private CachingRepository cachingRepository;

    public CachingCoinsServiceImpl(CachingRepository cachingRepository) {
        this.cachingRepository = cachingRepository;
    }

    @Override
    public void cachingCoin(List<CoinBo> coins) {
        cachingRepository.save(CachingKey.COIN, coins);
    }

}
