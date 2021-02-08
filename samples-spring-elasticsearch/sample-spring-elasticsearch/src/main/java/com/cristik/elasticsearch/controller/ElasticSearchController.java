package com.cristik.elasticsearch.controller;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author cristik
 */
@RestController
@RequestMapping(value = "/es")
public class ElasticSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchController.class);

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IndicesClient indicesClient;

    @GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public String create() throws IOException {
        IndexRequest indexRequest = new IndexRequest("test");
        indexRequest.source(XContentType.JSON, "{}");
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

}
