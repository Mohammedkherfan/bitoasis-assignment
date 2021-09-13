package com.bitoasis.assignment.service;

import com.bitoasis.assignment.bo.TickerBo;

public interface FindTickerService {

    TickerBo find(String code);
}
