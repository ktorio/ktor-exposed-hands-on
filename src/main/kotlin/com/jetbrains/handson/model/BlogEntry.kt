package com.jetbrains.handson.model

import java.util.concurrent.atomic.AtomicInteger

class BlogEntry
private constructor(val id: Int, val headline: String, val body: String) {
    companion object {
        private val idCounter = AtomicInteger()

        fun newEntry(headline: String, body: String) =
            BlogEntry(idCounter.getAndIncrement(), headline, body)
    }
}

val blogEntries = mutableListOf(BlogEntry.newEntry(
    "The drive to develop!",
    "...it's what keeps me going."
))