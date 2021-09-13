package com.bitoasis.assignment.client;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinClientTest implements CoinClient {

    private List<CoinBo> coins;
    private Map<Long, TickerBo> ticker;

    public CoinClientTest() {
        this.coins = new ArrayList<>();
        this.coins.add(CoinBo.builder().id(1L).name("bit").code("BIT").build());
        this.coins.add(CoinBo.builder().id(2L).name("Nbit").code("NBIT").build());
        this.ticker = new HashMap<>();
        this.ticker.put(1L, TickerBo.builder().code("BIT").daily_change(2.1).last_updated(564565L).volume(151351L).build());
    }


    public List<CoinBo> setCoins(List<CoinBo> coins) {
        this.coins = coins;
        return coins;
    }

    public void setTicker(Map<Long, TickerBo> ticker) {
        this.ticker = ticker;
    }

    @Override
    public List<CoinBo> findAllCoins() {
        return coins;
    }

    @Override
    public TickerBo findTicker(Long id) {
        return ticker.get(id);
    }
}
