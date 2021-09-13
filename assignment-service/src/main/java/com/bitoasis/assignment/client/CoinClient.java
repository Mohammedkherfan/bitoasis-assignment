package com.bitoasis.assignment.client;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;

import java.util.List;

public interface CoinClient {

    List<CoinBo> findAllCoins();

    TickerBo findTicker(Long id);
}
