package com.cristik.elasticsearch.service;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cristik
 * @version 1.0
 * @date 2021/2/9 0:37
 * @since 1.8
 */
@Service
@RequiredArgsConstructor
public class IndexService {

    private static final Set<String> IGNORE_SETTING_NAMES = Sets.newHashSet(
            "index.creation_date",
            "index.version.created",
            "index.provided_name",
            "index.uuid");

    private final IndicesClient indicesClient;

    /**
     * Exists.
     *
     * @param indices the indices
     * @return the boolean
     */
    @SneakyThrows
    public boolean exists(String... indices) {
        return indicesClient.exists(new GetIndexRequest(indices), RequestOptions.DEFAULT);
    }

    /**
     * Gets index.
     *
     * @param indices the indices
     * @return the index
     */
    @SneakyThrows
    public GetIndexResponse getIndex(String... indices) {
        return indicesClient.get(new GetIndexRequest(indices), RequestOptions.DEFAULT);
    }

    /**
     * Create index.
     *
     * @param index the index
     * @return the create index response
     */
    @SneakyThrows
    public CreateIndexResponse createIndex(String index) {
        return indicesClient.create(new CreateIndexRequest(index), RequestOptions.DEFAULT);
    }

    /**
     * Put settings.
     *
     * @param index    the index
     * @param settings the settings
     * @return the acknowledged response
     */
    @SneakyThrows
    public AcknowledgedResponse putSettings(String index, Settings settings) {
        return indicesClient.putSettings(new UpdateSettingsRequest(index)
                .settings(settings), RequestOptions.DEFAULT);
    }

    /**
     * Gets mappings.
     *
     * @param indices the indices
     * @return the mappings
     */
    @SneakyThrows
    public Map<String, MappingMetaData> getMappings(String... indices) {
        return indicesClient.getMapping(new GetMappingsRequest()
                .indices(indices), RequestOptions.DEFAULT)
                .mappings();
    }

    /**
     * Gets single mappings.
     *
     * @param index the index
     * @return the single mappings
     */
    @SneakyThrows
    public Map<String, Object> getSingleMappings(String index) {
        return getMappings(index).get(index).getSourceAsMap();
    }

    /**
     * Put mappings.
     *
     * @param index         the index
     * @param mappingSource the mapping source
     * @return the acknowledged response
     */
    @SneakyThrows
    public AcknowledgedResponse putMappings(String index, Map<String, ?> mappingSource) {
        return indicesClient.putMapping(new PutMappingRequest(index)
                .source(mappingSource), RequestOptions.DEFAULT);
    }

    /**
     * Clone mappings.
     *
     * @param sourceIndex the source index
     * @param targetIndex the target index
     * @return the acknowledged response
     */
    @SneakyThrows
    public AcknowledgedResponse cloneMappings(String sourceIndex, String targetIndex) {
        Map<String, Object> mappings = getSingleMappings(sourceIndex);
        return putMappings(targetIndex, mappings);
    }

    /**
     * Clone index without data.
     *
     * @param sourceIndex the source index
     * @param targetIndex the target index
     * @return the create index response
     */
    @SneakyThrows
    public CreateIndexResponse cloneIndexWithoutData(String sourceIndex, String targetIndex) {
        if (exists(targetIndex)) {
            throw new ElasticsearchException("sourceIndex[{}] already existed", sourceIndex);
        }

        GetIndexResponse getIndexResponse = getIndex(sourceIndex);
        List<AliasMetaData> aliasMetadata = getIndexResponse.getAliases().get(sourceIndex);
        List<Alias> aliases = aliasMetadata.stream()
                .map(AliasMetaData::getAlias)
                .map(Alias::new)
                .collect(Collectors.toList());
        Settings settings = getIndexResponse.getSettings().get(sourceIndex);
        Settings filterSettings = settings.filter(name -> !IGNORE_SETTING_NAMES.contains(name));
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(targetIndex)
                .aliases(aliases)
                .settings(filterSettings)
                .mapping(getIndexResponse.getMappings().get(sourceIndex).sourceAsMap());
        return indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * Clone index.
     * To clone an index, the index must be marked as read-only and have a cluster health status of green.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clone-index.html">
     *
     * @param sourceIndex the source index
     * @param targetIndex the target index
     * @return the resize response
     */
    @SneakyThrows
    public ResizeResponse cloneIndex(String sourceIndex, String targetIndex) {
        return indicesClient.clone(new ResizeRequest(targetIndex, sourceIndex), RequestOptions.DEFAULT);
    }

    /**
     * Delete index.
     *
     * @param index the index
     * @return the acknowledged response
     */
    @SneakyThrows
    public AcknowledgedResponse deleteIndex(String index) {
        if (!exists(index)) {
            return new AcknowledgedResponse(true);
        }
        return indicesClient.delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
    }

    /**
     * Refresh.
     *
     * @param index the index
     * @return the refresh response
     */
    @SneakyThrows
    public RefreshResponse refresh(String index) {
        return indicesClient.refresh(new RefreshRequest(index), RequestOptions.DEFAULT);
    }
}
