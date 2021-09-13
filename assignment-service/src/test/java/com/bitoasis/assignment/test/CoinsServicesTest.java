package com.bitoasis.assignment.test;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.client.CoinClient;
import com.bitoasis.assignment.client.CoinClientTest;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.repository.CachingRepositoryTest;
import com.bitoasis.assignment.service.*;
import com.bitoasis.assignment.service.impl.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class CoinsServicesTest {

    private CachingRepository cachingRepository;

    private CachingCoinsService cachingCoinsService;
    private CollectCoinsService collectCoinsService;
    private FindCoinsService findAllCoinsService;
    private CachingTickerService cachingTickerService;
    private CollectTickerService collectTickerService;
    private FindTickerService findTickerService;

    private CoinClient coinClient;

    @Before
    public void setUp() throws Exception {
        cachingRepository = new CachingRepositoryTest();

        coinClient = new CoinClientTest();

        cachingCoinsService = new CachingCoinsServiceImpl(cachingRepository);
        collectCoinsService = new CollectCoinsServiceImpl(coinClient);
        findAllCoinsService = new FindCoinsServiceImpl(cachingRepository);
        collectTickerService = new CollectTickerServiceImpl(coinClient);
        cachingTickerService = new CachingTickerServiceImpl(cachingRepository, 5);
        findTickerService = new FindTickerServiceImpl(cachingRepository);
    }

    @Test
    public void whenCachingCoins_AndCoinsListIsEmpty_ThenShouldReturnSuccess() {
        cachingCoinsService.cachingCoin(new ArrayList<>());
    }

    @Test
    public void whenCachingCoins_AndCoinsListIsNotEmpty_ThenShouldReturnSuccess() {
        cachingCoinsService.cachingCoin(Arrays.asList(CoinBo.builder().name("Test").code("TST").build()));
    }

    @Test
    public void whenCollectCoinsFromThirdParty_AndReturnEmptyListFromThirdParty_ThenShouldReturnEmptyList() {
        CoinClientTest coinClient = new CoinClientTest();
        coinClient.setCoins(new ArrayList<>());
        collectCoinsService = new CollectCoinsServiceImpl(coinClient);
        List<CoinBo> coins = collectCoinsService.findAllCoins();
        Assert.assertTrue(coins.isEmpty());
    }

    @Test
    public void whenCollectCoinsFromThirdParty_AndReturnListOfCoinsFromThirdParty_ThenShouldReturnList() {
        List<CoinBo> coins = new ArrayList<>();
        coins.add(CoinBo.builder().code("TST1").name("test1").build());
        coins.add(CoinBo.builder().code("TST2").name("test2").build());
        CoinClientTest coinClientTest = new CoinClientTest();
        coinClientTest.setCoins(coins);
        collectCoinsService = new CollectCoinsServiceImpl(coinClientTest);
        List<CoinBo> list = collectCoinsService.findAllCoins();
        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void whenCallFindAllCoins_AndCachingIsEmpty_ThenShouldReturnEmptyList() {
        List<CoinBo> list = findAllCoinsService.findAll();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void whenCallFindAllCoins_AndCachingIsNotEmpty_ThenShouldReturnList() {
        List<CoinBo> coins = new ArrayList<>();
        coins.add(CoinBo.builder().code("TST1").name("test1").build());
        coins.add(CoinBo.builder().code("TST2").name("test2").build());
        CoinClientTest coinClientTest = new CoinClientTest();
        coinClientTest.setCoins(coins);
        collectCoinsService = new CollectCoinsServiceImpl(coinClientTest);
        cachingCoinsService.cachingCoin(collectCoinsService.findAllCoins());
        List<CoinBo> list = findAllCoinsService.findAll();
        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void whenCachingTicker_AndTickerIsValid_ThenShouldReturnSuccess() {
        cachingTickerService.cachingTicker("BIT", TickerBo.builder().volume(1L).last_updated(1L).daily_change(2.25).code("BIT").build());
    }

    @Test
    public void whenCollectTicker_AndCodeIsExist_ThenShouldReturnSuccess() {
        Map<Long, TickerBo> ticker = new HashMap<>();
        ticker.put(1L, TickerBo.builder().volume(1L).last_updated(1L).daily_change(2.25).code("BIT").build());
        CoinClientTest coinClientTest = new CoinClientTest();
        coinClientTest.setTicker(ticker);
        collectTickerService = new CollectTickerServiceImpl(coinClientTest);
        TickerBo tickerBo = collectTickerService.find(1L);
        Assert.assertNotNull(tickerBo);
    }

    @Test
    public void whenCollectTicker_AndTickerNotExist_ThenShouldReturnSuccess() {
        CoinClientTest coinClient = new CoinClientTest();
        coinClient.setTicker(new HashMap<>());
        collectTickerService = new CollectTickerServiceImpl(coinClient);
        TickerBo tickerBo = collectTickerService.find(1L);
        Assert.assertNull(tickerBo);
    }

    @Test
    public void whenCallFindTicker_AndCachingIsEmpty_ThenShouldReturnEmptyList() {
        TickerBo tickerBo = findTickerService.find("BTI");
        Assert.assertNull(tickerBo);
    }

    @Test
    public void whenCallFindTicker_AndCachingIsNotEmpty_ThenShouldReturnList() {
        Map<Long, TickerBo> ticker = new HashMap<>();
        ticker.put(1L, TickerBo.builder().volume(1L).last_updated(1L).daily_change(2.25).code("BIT").build());
        CoinClientTest coinClientTest = new CoinClientTest();
        coinClientTest.setTicker(ticker);
        collectTickerService = new CollectTickerServiceImpl(coinClientTest);
        TickerBo tickerBo = collectTickerService.find(1L);
        cachingTickerService.cachingTicker("BIT", tickerBo);
        TickerBo tickerBo1 = findTickerService.find("BIT");
        Assert.assertNotNull(tickerBo1);
    }
}
