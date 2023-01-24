package com.n8ify.commitextr

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.nio.file.Path

@SpringBootApplication
class CommitextrApplication

fun main(args: Array<String>) {
    runApplication<CommitextrApplication>(*args)
}


@Component
class ExtractRunner : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(ExtractRunner::class.java)

    override fun run(vararg args: String?) {
        if (args.isEmpty()) {
            throw IllegalArgumentException("Argument 0 is required as commit file path")
        }
        kotlin.runCatching {
            val path = args[0]
            val pathFile = Path.of(path)
            val objectMapper = ObjectMapper()
            val convert = ConvertUtil.convert(pathFile)
            println(objectMapper.writeValueAsString(convert))
            ExtractorUtil.extract(convert, pathFile)
        }.onFailure {
            logger.error("Unable to extract commit content file by date", it)
        }
    }
}