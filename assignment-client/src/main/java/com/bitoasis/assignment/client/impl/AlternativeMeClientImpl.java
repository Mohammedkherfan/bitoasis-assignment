package com.bitoasis.assignment.client.impl;

import com.bitoasis.assignment.bo.CoinBo;
import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.client.CoinClient;
import com.bitoasis.assignment.enums.ResponseCode;
import com.bitoasis.assignment.exception.CoinException;
import com.bitoasis.assignment.map.ErrorMap;
import com.bitoasis.assignment.response.CoinResponse;
import com.bitoasis.assignment.response.ErrorDto;
import com.bitoasis.assignment.response.TickerResponse;
import com.bitoasis.assignment.response.TickerResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class AlternativeMeClientImpl implements CoinClient {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    @Value("${coin.url.alternative-me}")
    private String url;

    public AlternativeMeClientImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<CoinBo> findAllCoins() {
        CoinResponse coins = callThirdPartyToFindAllCoins();
        if (isNull(coins))
            return new ArrayList<>();
        return coins.getCoins().stream()
                .map(coin -> CoinBo.builder()
                        .id(coin.getId())
                        .code(coin.getCode())
                        .name(coin.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TickerBo findTicker(Long id) {
        TickerResponseDto ticker = callThirdPartyToFindTicker(id).getResponse().get(id);
        if (isNull(ticker))
            throw new CoinException(ResponseCode.UNDEFINED_COIN, "Undefined coin " + id);
        return TickerBo.builder()
                .code(ticker.getCode())
                .daily_change(ticker.getQuotesDto().getCurrencyDto().getPercentageChange24h())
                .price(ticker.getQuotesDto().getCurrencyDto().getPrice())
                .volume(ticker.getQuotesDto().getCurrencyDto().getVolume())
                .last_updated(ticker.getLast_updated())
                .build();
    }

    private TickerResponse callThirdPartyToFindTicker(Long id) {
        try {
            long startTime = System.currentTimeMillis();
            log.info("Starting call AlternativeMe to find ticker, Time: {}", startTime);
            HttpEntity entity = new HttpEntity(buildHeader());
            String buildUrl = url + "/ticker/" + id + "/";
            ResponseEntity<TickerResponse> responseEntity = restTemplate.exchange(
                    buildUrl,
                    HttpMethod.GET,
                    entity,
                    TickerResponse.class);

            long endTime = System.currentTimeMillis();
            log.info("Finish call AlternativeMe to find ticker, Time: {}", startTime);
            log.info("after calling AlternativeMe to find ticker, response: {}, duration: {}", responseEntity, endTime - startTime);
            return responseEntity.getBody();
        }catch (HttpClientErrorException ex) {
            log.error("AlternativeMe to find ticker request following error: {} errorBody {}", ex, ex.getResponseBodyAsString());
            try {
                ErrorDto errorMessage = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorDto.class);
                log.error("AlternativeMe find ticker request following error {} {}", ex, ex.getResponseBodyAsString());
                throw new CoinException(ErrorMap.getResponseCode(ex.getStatusCode()), errorMessage.getResponseMessage());
            } catch (IOException e) {
                throw new CoinException(ResponseCode.ERROR_CALLING_THIRD_PARTY, e.getMessage());
            }
        } catch (Exception ex) {
            log.error("An error occured when calling AlternativeMe find ticker {}", ex);
            throw new CoinException(ResponseCode.ERROR_CALLING_THIRD_PARTY, ex.getMessage());
        }
    }

    private CoinResponse callThirdPartyToFindAllCoins() {
        try {
            long startTime = System.currentTimeMillis();
            log.info("Starting call AlternativeMe to list find all coins, Time: {}", startTime);
            HttpEntity entity = new HttpEntity(buildHeader());
            String buildUrl = url + "/listings/";
            ResponseEntity<CoinResponse> responseEntity = restTemplate.exchange(
                    buildUrl,
                    HttpMethod.GET,
                    entity,
                    CoinResponse.class);
            long endTime = System.currentTimeMillis();
            log.info("Finish call AlternativeMe to list find all coins, Time: {}", startTime);
            log.info("after calling AlternativeMe find all coins, response: {}, duration: {}", responseEntity, endTime - startTime);
            return responseEntity.getBody();
        }catch (HttpClientErrorException ex) {
            log.error("AlternativeMe list all find all coins request following error: {} errorBody {}", ex, ex.getResponseBodyAsString());
            try {
                ErrorDto errorMessage = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorDto.class);
                log.error("AlternativeMe list all find all coins request following error {} {}", ex, ex.getResponseBodyAsString());
                throw new CoinException(ErrorMap.getResponseCode(ex.getStatusCode()), errorMessage.getResponseMessage());
            } catch (IOException e) {
                log.error("AlternativeMe list all find all coins request following error {} {}", ex, ex.getResponseBodyAsString());
                throw new CoinException(ResponseCode.ERROR_CALLING_THIRD_PARTY, e.getMessage());
            }
        } catch (Exception ex) {
            log.error("An error occured when calling AlternativeMe list all findAllCoins {}", ex);
            throw new CoinException(ResponseCode.ERROR_CALLING_THIRD_PARTY, ex.getMessage());
        }
    }

    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }
}
