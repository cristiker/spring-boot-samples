package com.cristik.elasticsearch.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cristik
 * @version 1.0
 * @date 2021/2/9 0:49
 * @since 1.8
 */
@Service
@RequiredArgsConstructor
public class AggregationService {

    private static final String CARDINALITY_SUFFIX = "_cardinality";
    private static final String MAX_SUFFIX = "_max";
    private static final String MIN_SUFFIX = "_min";
    private static final String TERMS_SUFFIX = "_terms";

    private final RestHighLevelClient restHighLevelClient;

    /**
     * Distinct count.
     * See
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.x/search-aggregations-metrics-cardinality-aggregation.html">
     *
     * @param index the index
     * @param field the field
     * @return the distinct count
     */
    public long distinctCount(String index, String field) {
        AggregationBuilder aggregationBuilder = getCardinalityAggregation(field);
        ParsedCardinality parsedCardinality = (ParsedCardinality) aggregate(index, aggregationBuilder);
        return parsedCardinality.getValue();
    }

    /**
     * Min agg double.
     *
     * @param index the index
     * @param field the field
     * @return the double
     */
    public double minAgg(String index, String field) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.min(field + MIN_SUFFIX)
                .field(field);
        return ((NumericMetricsAggregation.SingleValue) aggregate(index, aggregationBuilder)).value();
    }

    /**
     * Max agg double.
     *
     * @param index the index
     * @param field the field
     * @return the double
     */
    public double maxAgg(String index, String field) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.max(field + MAX_SUFFIX)
                .field(field);
        return ((NumericMetricsAggregation.SingleValue) aggregate(index, aggregationBuilder)).value();
    }

    /**
     * Avg agg double.
     *
     * @param index the index
     * @param field the field
     * @return the double
     */
    public double avgAgg(String index, String field) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.avg(field + MIN_SUFFIX)
                .field(field);
        return ((NumericMetricsAggregation.SingleValue) aggregate(index, aggregationBuilder)).value();
    }

    /**
     * Terms count agg.
     *
     * @param index the index
     * @param field the field
     * @return the map
     */
    public Map<Object, Long> termsCountAgg(String index, String field) {
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms(field + TERMS_SUFFIX)
                .field(field);
        ParsedTerms parsedTerms = (ParsedTerms) aggregate(index, aggregationBuilder);
        return parsedTerms.getBuckets()
                .stream()
                .collect(Collectors.toMap(Terms.Bucket::getKey, Terms.Bucket::getDocCount));
    }

    private static AggregationBuilder getCardinalityAggregation(String field) {
        return AggregationBuilders.cardinality(field + CARDINALITY_SUFFIX)
                .field(field);
    }

    @SneakyThrows
    private Aggregation aggregate(String index, AggregationBuilder aggregationBuilder) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest(index)
                .source(sourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT)
                .getAggregations()
                .get(aggregationBuilder.getName());
    }
}
