package com.bitoasis.assignment.api.impl;

import com.bitoasis.assignment.api.TickerController;
import com.bitoasis.assignment.manager.CoinManager;
import com.bitoasis.assignment.response.CoinTickerResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Api(value = "Ticker Api")
public class TickerControllerImpl implements TickerController {

    private CoinManager coinManager;

    @Autowired
    public TickerControllerImpl(CoinManager coinManager) {
        this.coinManager = coinManager;
    }

    @Override
    public CoinTickerResponse findTicker(@PathVariable @Valid @NotBlank(message = "Invalid coin code") String coin_code,
                                         @RequestHeader("Authorization") String authorization) {
        return coinManager.findTicker(coin_code);
    }
}
