package com.jetbrains.handson.dao

import com.jetbrains.handson.model.BlogEntry

interface DAOFacade {
    suspend fun addNewBlogEntry(headline: String, body: String): BlogEntry?
    suspend fun blogEntry(id: Int): BlogEntry?
    suspend fun deleteBlogEntry(id: Int): Boolean
    suspend fun allBlogEntries(): List<BlogEntry>
}