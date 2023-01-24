package com.n8ify.commitextr

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object ConvertUtil {

    private val logger = LoggerFactory.getLogger(ConvertUtil::class.java)

    fun convert(commitFilePath: Path) : List<CommitTemplate> {

        /* Initial file and lines */
        val lines = Files.readAllLines(commitFilePath)
        val sections = proceedSection(lines)

        /* Initial storing object */
        val templates = mutableListOf<CommitTemplate>()

        /* Iterate and proceed line */
        for (section in sections) {

            var hash: String = ""
            var author: String = ""
            var date: String = ""
            var activities: MutableList<String> = mutableListOf()
            var isMerge: Boolean = false

            for (line in section) {
                when {
                    line.startsWith(Constant.COMMIT_PREFIX) -> {
                        hash = line.split(Constant.EMPTY)[1].trim()
                    }

                    line.startsWith(Constant.AUTHOR_PREFIX) -> {
                        author = line.replace(Constant.AUTHOR_PREFIX, Constant.EMPTY)
                    }

                    line.startsWith(Constant.DATE_PREFIX) -> {
                        val splitDateData = line.split(Constant.EMPTY);
                        date = "${splitDateData[4]} ${splitDateData[5]} ${splitDateData[7]}"
                    }

                    else -> {
                        if (line.trim().startsWith(Constant.CHERRY_PICK_ACTIVITY_PREFIX)
                                || line.trim().startsWith(Constant.CONFLICTED_ACTIVITY_PREFIX)
                                || line.trim().startsWith(Constant.REVERTED_ACTIVITY_PREFIX)
                                || line.trim().startsWith(Constant.REVERTED_VERBOSE_ACTIVITY_PREFIX)
                                || line.trim().startsWith(Constant.SKIPPING_ACTIVITY_PREFIX)) {
                            logger.warn("Skip Exclusion line (Generic): $line")
                        } else if (line.trim().startsWith(Constant.MERGE_BRANCH_ACTIVITY_PREFIX)) {
                            logger.warn("Skip Exclusion line (Merge): $line")
                            isMerge = true
                        } else {
                            val record = line.trim();
                            if (record.isNotBlank()) {
                                activities.add(record)
                            }
                        }
                    }
                }
            }

            templates.add(CommitTemplate(
                    hash = hash,
                    author = author,
                    date = date,
                    activities = activities,
                    isMerge = isMerge
            ))

        }

        /* Return finalize value */
        return templates

    }

    private fun proceedSection(lines: List<String>) : List<List<String>> {
        val proceedLines = mutableListOf<List<String>>()

        var section : MutableList<String> = mutableListOf<String>()
        var first = true
        lines.forEach { line ->
            if (line.startsWith(Constant.COMMIT_PREFIX)) {
                section = mutableListOf<String>()
                if (!first) {
                    proceedLines.add(section)
                } else {
                    first = false
                }
            }
            section.add(line)
        }

        return proceedLines;
    }

}