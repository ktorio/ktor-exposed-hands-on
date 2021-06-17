package com.jetbrains.handson.dao

import com.jetbrains.handson.dao.DatabaseFactory.dbQuery
import com.jetbrains.handson.model.BlogEntries
import com.jetbrains.handson.model.BlogEntry
import org.jetbrains.exposed.sql.*

class DAOFacadeImpl : DAOFacade {

    override suspend fun addNewBlogEntry(
        headline: String, body: String
    ): BlogEntry? = dbQuery {
        val insertStatement = BlogEntries.insert {
            it[BlogEntries.headline] = headline
            it[BlogEntries.body] = body
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toBlogEntry)
    }

    override suspend fun blogEntry(id: Int): BlogEntry? = dbQuery {
        BlogEntries
            .select { BlogEntries.id eq id }
            .map(::toBlogEntry)
            .singleOrNull()
    }

    override suspend fun deleteBlogEntry(id: Int): Boolean = dbQuery {
        BlogEntries.deleteWhere { BlogEntries.id eq id } > 0
    }

    override suspend fun allBlogEntries(): List<BlogEntry> = dbQuery {
        BlogEntries.selectAll().map(::toBlogEntry)
    }

    private fun toBlogEntry(row: ResultRow) = BlogEntry(
        id = row[BlogEntries.id],
        headline = row[BlogEntries.headline],
        body = row[BlogEntries.body],
    )
}