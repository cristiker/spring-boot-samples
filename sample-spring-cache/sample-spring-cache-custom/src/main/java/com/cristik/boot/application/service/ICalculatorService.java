package com.cristik.boot.application.service;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author cristik
 */
public interface ICalculatorService {
    @Cacheable("Factorials")
    long factorial(long number);

    @Cacheable("Factorials")
    List<Long> factorials(List<Long> values);
}
