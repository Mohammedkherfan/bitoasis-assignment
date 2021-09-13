package com.bitoasis.assignment.test;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.client.CoinClient;
import com.bitoasis.assignment.client.CoinClientTest;
import com.bitoasis.assignment.dto.CoinDto;
import com.bitoasis.assignment.enums.Sort;
import com.bitoasis.assignment.exception.CoinException;
import com.bitoasis.assignment.manager.CoinManager;
import com.bitoasis.assignment.manager.impl.CoinManagerImpl;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.repository.CachingRepositoryTest;
import com.bitoasis.assignment.response.CoinTickerResponse;
import com.bitoasis.assignment.service.*;
import com.bitoasis.assignment.service.impl.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CoinsManagerTest {

    private CachingRepository cachingRepository;

    private CollectCoinsService collectCoinsService;
    private CachingCoinsService cachingCoinsService;
    private FindCoinsService findCoinsService;
    private CollectTickerService collectTickerService;
    private FindTickerService findTickerService;
    private CachingTickerService cachingTickerService;

    private CoinClient coinClient;

    private CoinManager coinManager;

    @Before
    public void setUp() throws Exception {
        cachingRepository = new CachingRepositoryTest();

        coinClient = new CoinClientTest();

        cachingCoinsService = new CachingCoinsServiceImpl(cachingRepository);
        collectCoinsService = new CollectCoinsServiceImpl(coinClient);
        findCoinsService = new FindCoinsServiceImpl(cachingRepository);
        collectTickerService = new CollectTickerServiceImpl(coinClient);
        cachingTickerService = new CachingTickerServiceImpl(cachingRepository, 5);
        findTickerService = new FindTickerServiceImpl(cachingRepository);

        coinManager = new CoinManagerImpl(collectCoinsService, cachingCoinsService, findCoinsService, collectTickerService, findTickerService, cachingTickerService);
    }

    @Test
    public void whenCachingCoin_ThenShouldReturnSuccess() {
        coinManager.startCaching();
        List<CoinBo> coins = findCoinsService.findAll();
        Assert.assertTrue(!coins.isEmpty());
    }

    @Test
    public void whenListAllCoins_AndNoConsCached_ThenShouldReturnEmptyList() {
        List<CoinDto> list = coinManager.findAll(Sort.ASC);
        Assert.assertTrue(list.isEmpty());
    }

    @Test(expected = CoinException.class)
    public void whenListAllCoins_AndSortInNull_ThenShouldReturnEmptyList() {
        coinManager.findAll(null);
    }

    @Test
    public void whenListAllCoins_AndConsCacheNotEmpty_ThenShouldReturnList() {
        coinManager.startCaching();
        List<CoinDto> list = coinManager.findAll(Sort.ASC);
        Assert.assertTrue(!list.isEmpty());
    }

    @Test(expected = CoinException.class)
    public void whenFindTicker_AndCoinCodeNotFound_ThenShouldReturnError() {
        coinManager.findTicker("AAA");
    }

    @Test(expected = CoinException.class)
    public void whenFindTicker_AndCoinIdNotFound_ThenShouldReturnError() {
        coinManager.startCaching();
        coinManager.findTicker("NBIT");
    }

    @Test
    public void whenFindTicker_AndCoinIdExist_ThenShouldReturnSuccess() {
        coinManager.startCaching();
        CoinTickerResponse response = coinManager.findTicker("BIT");
        Assert.assertNotNull(response);
    }
}
