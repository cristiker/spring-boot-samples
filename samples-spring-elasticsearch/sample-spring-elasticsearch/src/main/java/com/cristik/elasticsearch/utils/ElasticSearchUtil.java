package com.cristik.elasticsearch.utils;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author cristik
 * @version 1.0
 * @date 2021/2/9 0:07
 * @since 1.8
 */
public class ElasticSearchUtil {

    private ElasticSearchUtil() {
    }

    public static RestHighLevelClient createRestHighLevelClient(String esUrl) {
        return new RestHighLevelClient(getRestClientBuilder(esUrl));
    }

    public static RestHighLevelClient createRestHighLevelClient(String esIp, Integer esPort) {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(esIp, esPort, "http")));
    }

    public static RestHighLevelClient createRestHighLevelClient(String esUrl, String userName, String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        RestClientBuilder restClientBuilder = getRestClientBuilder(esUrl)
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });
        return new RestHighLevelClient(restClientBuilder);
    }

    public static RestHighLevelClient createRestHighLevelClientWithKeepAlive(String esUrl, Long keepAlive) {
        RestClientBuilder clientBuilder = getRestClientBuilder(esUrl)
                .setHttpClientConfigCallback(requestConfig ->
                        requestConfig.setKeepAliveStrategy((response, context) -> keepAlive));
        return new RestHighLevelClient(clientBuilder);
    }

    public static RestClientBuilder getRestClientBuilder(String esUrl) {
        return RestClient.builder(createHttpHost(URI.create(esUrl)));
    }

    public static HttpHost createHttpHost(URI uri) {
        if (StringUtils.isEmpty(uri.getUserInfo())) {
            return HttpHost.create(uri.toString());
        }
        try {
            return HttpHost.create(new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(),
                    uri.getQuery(), uri.getFragment()).toString());
        } catch (URISyntaxException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
