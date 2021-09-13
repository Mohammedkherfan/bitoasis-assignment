package com.bitoasis.assignment.runner;

import com.bitoasis.assignment.manager.CoinManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class CachingCoinsRunner implements CommandLineRunner {

    private CoinManager coinManager;

    @Autowired
    public CachingCoinsRunner(CoinManager coinManager) {
        this.coinManager = coinManager;
    }

    @Override
    public void run(String... args) throws Exception {
        startCaching();
    }

    private void startCaching() {
        log.info("Start collect findAllCoins fo cachingCoin {}", LocalDateTime.now());
        coinManager.startCaching();
        log.info("Finish collect findAllCoins fo cachingCoin {}", LocalDateTime.now());
    }
}
