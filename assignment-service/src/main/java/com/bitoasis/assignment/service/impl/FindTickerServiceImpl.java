package com.bitoasis.assignment.service.impl;

import com.bitoasis.assignment.bo.TickerBo;
import com.bitoasis.assignment.repository.CachingRepository;
import com.bitoasis.assignment.service.FindTickerService;

public class FindTickerServiceImpl implements FindTickerService {

    private CachingRepository cachingRepository;

    public FindTickerServiceImpl(CachingRepository cachingRepository) {
        this.cachingRepository = cachingRepository;
    }

    @Override
    public TickerBo find(String code) {
        return cachingRepository.find(code);
    }
}
