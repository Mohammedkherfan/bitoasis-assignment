package com.bitoasis.assignment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CoinTickerResponse {

    private String code;
    private Double price;
    private Long volume;
    private Double daily_change;
    private Long last_updated;

}
