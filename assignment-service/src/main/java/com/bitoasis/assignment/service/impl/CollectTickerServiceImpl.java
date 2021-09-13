package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.client.CoinClient;
import com.bitoasis.assignment.service.CollectTickerService;

public class CollectTickerServiceImpl implements CollectTickerService {

    private CoinClient coinClient;

    public CollectTickerServiceImpl(CoinClient coinClient) {
        this.coinClient = coinClient;
    }

    @Override
    public TickerBo find(Long id) {
        TickerBo tickerBo = coinClient.findTicker(id);
        return tickerBo;
    }
}
