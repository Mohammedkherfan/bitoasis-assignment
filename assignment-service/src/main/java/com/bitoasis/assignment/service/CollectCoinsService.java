package com.bitoasis.assignment.service;

import com.bitoasis.assignment.bo.CoinBo;

import java.util.List;

public interface CollectCoinsService {

    List<CoinBo> findAllCoins();
}
