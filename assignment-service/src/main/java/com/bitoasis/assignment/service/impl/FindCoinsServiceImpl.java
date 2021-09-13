package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.enums.CachingKey;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.service.FindCoinsService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class FindCoinsServiceImpl implements FindCoinsService {

    private CachingRepository cachingRepository;

    public FindCoinsServiceImpl(CachingRepository cachingRepository) {
        this.cachingRepository = cachingRepository;
    }

    @Override
    public List<CoinBo> findAll() {
        List<CoinBo> coinBos = cachingRepository.findAll(CachingKey.COIN);
        if (isNull(coinBos))
            return new ArrayList<>();
        return coinBos;
    }
}
