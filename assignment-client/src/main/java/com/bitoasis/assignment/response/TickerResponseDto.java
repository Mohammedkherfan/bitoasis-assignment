package com.bitoasis.assignment.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerResponseDto implements Serializable {

    @JsonProperty(value = "symbol")
    private String code;
    @JsonProperty(value = "last_updated")
    private Long last_updated;
    @JsonProperty(value = "quotes")
    private QuotesDto quotesDto;

}
