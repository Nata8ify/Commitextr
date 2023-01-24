package com.n8ify.commitextr

object Constant {

    const val COMMIT_PREFIX = "commit ";
    const val MERGE_PREFIX = "Merge: ";
    const val AUTHOR_PREFIX = "Author: ";
    const val DATE_PREFIX = "Date:   ";
    const val MERGE_BRANCH_ACTIVITY_PREFIX = "Merge branch ";
    const val CHERRY_PICK_ACTIVITY_PREFIX = "(cherry picked ";
    const val CONFLICTED_ACTIVITY_PREFIX = "# ";
    const val REVERTED_ACTIVITY_PREFIX = "Revert ";
    const val REVERTED_VERBOSE_ACTIVITY_PREFIX = "This reverts commit ";
    const val SKIPPING_ACTIVITY_PREFIX = ":...skipping...";
    const val EMPTY = " ";

    const val NEW_LINE_EXTRACTED_CONTENT = "\n---------------------------------\n"

}