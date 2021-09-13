package com.bitoasis.assignment.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@AllArgsConstructor
public class CoinBo implements Serializable {

    private Long id;
    private String name;
    private String code;
}
