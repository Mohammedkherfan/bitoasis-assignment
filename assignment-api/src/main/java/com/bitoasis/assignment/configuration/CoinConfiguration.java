package com.bitoasis.assignment.configuration;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.client.CoinClient;
import com.bitoasis.assignment.client.impl.AlternativeMeClientImpl;
import com.bitoasis.assignment.manager.CoinManager;
import com.bitoasis.assignment.manager.impl.CoinManagerImpl;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.repository.impl.CachingRepositoryImpl;
import com.bitoasis.assignment.service.*;
import com.bitoasis.assignment.service.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Configuration
public class CoinConfiguration {

    @Bean
    public CoinClient coinClient() {
        return new AlternativeMeClientImpl();
    }

    @Bean
    public CollectCoinsService collectCoinsService(CoinClient coinClient) {
        return new CollectCoinsServiceImpl(coinClient);
    }

    @Bean
    public CoinManager coinManager(CollectCoinsService collectCoinsService,
                                   CachingCoinsService cachingCoinsService,
                                   FindCoinsService findAllCoinsService,
                                   CollectTickerService collectTickerService,
                                   FindTickerService findTickerService,
                                   CachingTickerService cachingTickerService) {
        return new CoinManagerImpl(collectCoinsService, cachingCoinsService, findAllCoinsService, collectTickerService, findTickerService, cachingTickerService);
    }

    @Bean
    public CachingTickerService cachingTickerService(CachingRepository cachingRepository, @Value("${spring.redis.time.ticker}") Integer cachingTickerTime) {
        return new CachingTickerServiceImpl(cachingRepository, cachingTickerTime);
    }

    @Bean
    public FindTickerService findTickerService(CachingRepository cachingRepository) {
        return new FindTickerServiceImpl(cachingRepository);
    }

    @Bean
    public CollectTickerService collectTickerService(CoinClient coinClient) {
        return new CollectTickerServiceImpl(coinClient);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory(String host, Integer port) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate<String, List<CoinBo>> redisTemplate(@Value("${spring.redis.host}") String host, @Value("${spring.redis.port}") Integer port) {
        RedisTemplate<String, List<CoinBo>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory(host, port));
        return redisTemplate;
    }

    @Bean
    public CachingRepository cachingRepository(RedisTemplate redisTemplate) {
        return new CachingRepositoryImpl(redisTemplate);
    }

    @Bean
    public CachingCoinsService cachingCoinsService(CachingRepository cachingRepository) {
        return new CachingCoinsServiceImpl(cachingRepository);
    }

    @Bean
    public FindCoinsService findCoinsService(CachingRepository cachingRepository) {
        return new FindCoinsServiceImpl(cachingRepository);
    }
}
