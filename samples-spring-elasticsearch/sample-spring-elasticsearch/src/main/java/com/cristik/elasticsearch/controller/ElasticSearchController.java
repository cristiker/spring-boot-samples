package com.cristik.elasticsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.cristik.elasticsearch.service.AggregationService;
import com.cristik.elasticsearch.service.DocumentService;
import com.cristik.elasticsearch.service.IndexService;
import com.cristik.elasticsearch.service.SearchService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IndexService indexService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private AggregationService aggregationService;

    @GetMapping(value = "/create/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String create(@PathVariable String index) throws IOException {
        CreateIndexResponse response = indexService.createIndex(index);
        indexService.createIndex(index, new HashMap<>(), new HashMap<>(), new HashMap<>());
        return response.index();
    }

    @GetMapping(value = "/index/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String queryIndex(@PathVariable String index) throws IOException {
        GetIndexResponse response = indexService.getIndex(index);
        Map<String, List<AliasMetaData>> aliasMetaDataMap = response.getAliases();
        String[] indices = response.getIndices();
        Map<String, MappingMetaData> mappingMetaDataMap = response.getMappings();
        Map<String, Settings> settingsMap = response.getSettings();
        Map<String, Settings> defaultSettingMap = response.getDefaultSettings();
        return response.getIndices()[0];
    }

}