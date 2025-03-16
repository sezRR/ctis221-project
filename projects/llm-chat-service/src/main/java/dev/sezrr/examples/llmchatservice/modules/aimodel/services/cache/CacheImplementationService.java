package dev.sezrr.examples.llmchatservice.modules.aimodel.services.cache;

import java.util.List;

/*
 *   This interface is used to define the methods that will be implemented in the cache service.
 */
public interface CacheImplementationService<T> {
    List<T> findAllCache();
}
