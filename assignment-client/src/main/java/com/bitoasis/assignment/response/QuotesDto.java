package com.bitoasis.assignment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuotesDto {

    @JsonProperty("USD")
    private CurrencyDto currencyDto;
}
