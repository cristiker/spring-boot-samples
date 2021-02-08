package com.cristik.elasticsearch.config;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cristik
 * @version 1.0
 * @date 2021/2/9 0:04
 * @since 1.8
 */
@Configuration
public class ElasticsearchConfiguration {

    private RestHighLevelClient restHighLevelClient;

    public ElasticsearchConfiguration(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Bean
    public IndicesClient indicesClient() {
        return restHighLevelClient.indices();
    }

}
