package com.bitoasis.assignment.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
public class TickerBo implements Serializable {

    private String code;
    private Double price;
    private Long volume;
    private Double daily_change;
    private Long last_updated;
}
