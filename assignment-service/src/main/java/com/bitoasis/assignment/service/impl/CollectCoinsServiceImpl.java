package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.client.CoinClient;
import com.bitoasis.assignment.service.CollectCoinsService;

import java.util.List;

public class CollectCoinsServiceImpl implements CollectCoinsService {

    private CoinClient coinClient;

    public CollectCoinsServiceImpl(CoinClient coinClient) {
        this.coinClient = coinClient;
    }

    @Override
    public List<CoinBo> findAllCoins() {
        return coinClient.findAllCoins();
    }
}
