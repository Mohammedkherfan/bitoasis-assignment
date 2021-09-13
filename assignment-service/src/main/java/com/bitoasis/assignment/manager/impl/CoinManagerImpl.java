package com.bitoasis.assignment.manager.impl;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.dto.CoinDto;
import com.bitoasis.assignment.enums.ResponseCode;
import com.bitoasis.assignment.enums.Sort;
import com.bitoasis.assignment.exception.CoinException;
import com.bitoasis.assignment.manager.CoinManager;
import com.bitoasis.assignment.response.CoinTickerResponse;
import com.bitoasis.assignment.service.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class CoinManagerImpl implements CoinManager {


    private CollectCoinsService collectCoinsService;
    private CachingCoinsService cachingCoinsService;
    private FindCoinsService findCoinsService;
    private CollectTickerService collectTickerService;
    private FindTickerService findTickerService;
    private CachingTickerService cachingTickerService;

    public CoinManagerImpl(CollectCoinsService collectCoinsService,
                           CachingCoinsService cachingCoinsService,
                           FindCoinsService findAllCoinsService,
                           CollectTickerService collectTickerService,
                           FindTickerService findTickerService,
                           CachingTickerService cachingTickerService) {
        this.collectCoinsService = collectCoinsService;
        this.cachingCoinsService = cachingCoinsService;
        this.findCoinsService = findAllCoinsService;
        this.collectTickerService = collectTickerService;
        this.findTickerService = findTickerService;
        this.cachingTickerService = cachingTickerService;
    }

    @Override
    public void startCaching() {
        List<CoinBo> coins = collectCoinsService.findAllCoins();
        cachingCoinsService.cachingCoin(coins);
    }

    @Override
    public List<CoinDto> findAll(Sort sorting) {
        if (isNull(sorting)) {
            throw new CoinException(ResponseCode.INVALID_REQUEST, "Invalid sort value " + sorting);
        }
        List<CoinBo> coins = findCoinsService.findAll();
        if (isNull(coins) || coins.isEmpty())
            return new ArrayList<>();
        if (Sort.ASC.equals(sorting))
            return coins.stream()
                    .map(coinBo -> CoinDto.builder()
                            .name(coinBo.getName())
                            .code(coinBo.getCode())
                            .build())
                    .sorted(Comparator.comparing(CoinDto::getName))
                    .collect(Collectors.toList());
        else
            return coins.stream()
                    .map(coinBo -> CoinDto.builder()
                            .name(coinBo.getName())
                            .code(coinBo.getCode())
                            .build())
                    .sorted(Comparator.comparing(CoinDto::getName).reversed())
                    .collect(Collectors.toList());
    }


    @Override
    public CoinTickerResponse findTicker(String coin_code) {
        TickerBo tickerBo = getTicker(coin_code);
        return CoinTickerResponse.builder()
                .code(tickerBo.getCode())
                .daily_change(tickerBo.getDaily_change())
                .last_updated(tickerBo.getLast_updated())
                .price(tickerBo.getPrice())
                .volume(tickerBo.getVolume())
                .build();
    }

    private TickerBo getTicker(String coin_code) {
        TickerBo tickerBo = findTickerService.find(coin_code);

        if (isNull(tickerBo)) {
            Optional<CoinBo> coin = findCoinsService.findAll().stream().filter(coinDto -> coin_code.equalsIgnoreCase(coinDto.getCode())).findFirst();
            if (!coin.isPresent())
                throw new CoinException(ResponseCode.UNDEFINED_COIN, "Coin undefined " + coin_code);

            TickerBo ticker = collectTickerService.find(coin.get().getId());
            if (isNull(ticker))
                throw new CoinException(ResponseCode.UNDEFINED_COIN, "Coin undefined " + coin_code);

            cachingTickerService.cachingTicker(coin.get().getCode(), ticker);
            return ticker;
        }else {
            return tickerBo;
        }
    }
}
