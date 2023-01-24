package com.n8ify.commitextr

import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileWriter
import java.nio.file.Path

object ExtractorUtil {

    private val logger = LoggerFactory.getLogger(ExtractorUtil::class.java)

    fun extract(templates: List<CommitTemplate>, commitFilePath: Path) {

        /* Create extracted file */
        val extractedFile = commitFilePath.toFile().run {
            val extractedFileName = name + "_extracted." + extension
            val createExtractedFile = File(parentFile.absoluteFile, extractedFileName)
            if (!createExtractedFile.exists() and createExtractedFile.createNewFile()) {
                logger.info("Extracted File is Created [${createExtractedFile.absolutePath}]")
            }
            createExtractedFile
        }

        /* Feed data to extracted file */
        FileWriter(extractedFile).use { writer ->
            templates
                    .filter { !it.isMerge }
                    .groupBy { it.date }
                    .forEach { entry ->
                        writer.appendLine()
                        writer.write(entry.key)
                        writer.appendLine()
                        entry.value.forEach {
                            it.activities
                                    .distinct()
                                    .forEach { activity ->
                                        writer.append(activity)
                                        writer.appendLine()
                                    }
                        }
                        writer.write(Constant.NEW_LINE_EXTRACTED_CONTENT)
                    }
        }


    }


}