package com.bitoasis.assignment.api;

import com.bitoasis.assignment.constant.Constant;
import com.bitoasis.assignment.dto.CoinDto;
import com.bitoasis.assignment.enums.Sort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping
public interface CoinController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/"+Constant.COINS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method to list all coins", notes = "This method used when want to list all coins")
    @ApiParam(value = "Parameter of operation", name = "sort")
    List<CoinDto> findAll(Sort sort);
}
