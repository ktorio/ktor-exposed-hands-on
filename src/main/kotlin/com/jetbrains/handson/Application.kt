package com.jetbrains.handson

import com.jetbrains.handson.model.BlogEntry
import com.jetbrains.handson.model.blogEntries
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries)))
        }
        get("/entry/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("view_entry.ftl", mapOf("entry" to blogEntries.find { it.id == id })))
        }
        get("/new") {
            call.respond(FreeMarkerContent("new_entry.ftl", model = null))
        }
        post("/submit") {
            val params = call.receiveParameters()
            val action = params.getOrFail("action")
            when (action) {
                "delete" -> {
                    val id = params.getOrFail("id")
                    blogEntries.removeIf { it.id.toString() == id }
                }
                "add" -> {
                    val headline = params.getOrFail("headline")
                    val body = params.getOrFail("body")
                    val newEntry = BlogEntry.newEntry(headline, body)
                    blogEntries.add(0, newEntry)
                }
            }
            call.respondRedirect("/")
        }
    }
}