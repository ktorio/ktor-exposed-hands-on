package com.jetbrains.handson.dao

import com.jetbrains.handson.model.BlogEntry
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration
import java.io.File

class DAOFacadeCacheImpl(
    private val delegate: DAOFacade,
    storagePath: File
) : DAOFacade {
    private val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerPersistenceConfiguration(storagePath))
        .withCache(
            "entriesCache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Int::class.javaObjectType,
                BlogEntry::class.java,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1000, EntryUnit.ENTRIES)
                    .offheap(10, MemoryUnit.MB)
                    .disk(100, MemoryUnit.MB, true)
            )
        )
        .build(true)

    private val entriesCache = cacheManager.getCache("entriesCache", Int::class.javaObjectType, BlogEntry::class.java)

    override suspend fun addNewBlogEntry(headline: String, body: String): BlogEntry? =
        delegate.addNewBlogEntry(headline, body)
            ?.also { entry -> entriesCache.put(entry.id, entry) }

    override suspend fun blogEntry(id: Int): BlogEntry? =
        entriesCache[id]
            ?: delegate.blogEntry(id)
                ?.also { entry -> entriesCache.put(id, entry) }

    override suspend fun deleteBlogEntry(id: Int): Boolean {
        entriesCache.remove(id)
        return delegate.deleteBlogEntry(id)
    }

    override suspend fun allBlogEntries(): List<BlogEntry> =
        delegate.allBlogEntries()
}