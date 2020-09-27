package com.cristik.boot.common;

import org.springframework.cache.Cache;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.StreamSupport;

/**
 * @author cristik
 */
public class ConcurrentMapCollectionHandlingDecoratedCache extends CollectionHandlingDecoratedCache {

    public ConcurrentMapCollectionHandlingDecoratedCache(final Cache cache) {
        super(cache);
    }

    @Override
    protected boolean areAllKeysPresentInCache(Iterable<?> keys) {

        ConcurrentMap nativeCache = (ConcurrentMap) getNativeCache();

        return StreamSupport.stream(keys.spliterator(), false).allMatch(nativeCache::containsKey);
    }

}
