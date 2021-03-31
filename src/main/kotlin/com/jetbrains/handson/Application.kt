package com.jetbrains.handson

import com.jetbrains.handson.model.BlogEntry
import com.jetbrains.handson.model.blogEntries
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("entries" to blogEntries)))
        }
        static("/static") {
            resources("files")
        }
        post("/submit") {
            val params = call.receiveParameters()
            val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val body = params["body"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val newEntry = BlogEntry(headline, body)
            blogEntries.add(0, newEntry)
            call.respondRedirect("/")
        }
    }
}