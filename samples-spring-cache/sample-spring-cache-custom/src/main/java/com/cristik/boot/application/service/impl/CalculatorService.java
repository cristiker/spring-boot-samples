package com.cristik.aop.log.service.impl;

import com.cristik.aop.log.service.ICalculatorService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cristik
 */
@Service
public class CalculatorService implements ICalculatorService {

    private boolean cacheMiss;

    public synchronized boolean isCacheMiss() {

        boolean cacheMiss = this.cacheMiss;

        setCacheMiss(false);

        return cacheMiss;
    }

    public synchronized void setCacheMiss(boolean cacheMiss) {
        this.cacheMiss = cacheMiss;
    }

    @Override
    @Cacheable("Factorials")
    public long factorial(long number) {
        Assert.isTrue(number >= 0,
                String.format("Number [%d] must be greater than equal to 0", number));
        setCacheMiss(true);
        if (number <= 2L) {
            return number == 2L ? 2L : 1L;
        }
        long result = number;
        while (--number > 0L) {
            result *= number;
        }
        return result;
    }

    @Override
    @Cacheable("Factorials")
    public List<Long> factorials(List<Long> values) {
        setCacheMiss(true);
        List<Long> results = new ArrayList<>(values.size());
        results.addAll(values.stream().map(this::factorial).collect(Collectors.toList()));
        return results;
    }
}