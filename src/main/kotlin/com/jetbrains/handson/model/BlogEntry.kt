package com.jetbrains.handson.model

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class BlogEntry(val id: Int, val headline: String, val body: String): Serializable

object BlogEntries : Table() {
    val id = integer("id").autoIncrement()
    val headline = varchar("headline", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}