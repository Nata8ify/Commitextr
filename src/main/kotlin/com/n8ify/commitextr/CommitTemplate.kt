package com.n8ify.commitextr

data class CommitTemplate (
        val hash: String,
        val author: String,
        val date: String,
        val activities: List<String>,
        val isMerge: Boolean
)
