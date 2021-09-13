package com.bitoasis.assignment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyDto {

    @JsonProperty("price")
    public Double price;
    @JsonProperty("volume_24h")
    public Long volume;
    @JsonProperty("market_cap")
    public Long marketCap;
    @JsonProperty("percentage_change_1h")
    public Double percentageChange1h;
    @JsonProperty("percentage_change_24h")
    public Double percentageChange24h;
    @JsonProperty("percentage_change_7d")
    public Double percentageChange7d;
    @JsonProperty("percent_change_1h")
    public Double percentChange1h;
    @JsonProperty("percent_change_24h")
    public Double percentChange24h;
    @JsonProperty("percent_change_7d")
    public Double percentChange7d;
}
