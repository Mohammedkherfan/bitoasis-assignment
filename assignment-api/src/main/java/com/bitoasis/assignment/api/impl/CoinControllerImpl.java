package com.bitoasis.assignment.api.impl;

import com.bitoasis.assignment.api.CoinController;
import com.bitoasis.assignment.dto.CoinDto;
import com.bitoasis.assignment.enums.Sort;
import com.bitoasis.assignment.manager.CoinManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "Coin Api")
public class CoinControllerImpl implements CoinController {

    private CoinManager coinManager;

    @Autowired
    public CoinControllerImpl(CoinManager coinManager) {
        this.coinManager = coinManager;
    }

    @Override
    public List<CoinDto> findAll(@RequestParam(required = false, defaultValue = "ASC" ) Sort sort) {
        return coinManager.findAll(sort);
    }
}
