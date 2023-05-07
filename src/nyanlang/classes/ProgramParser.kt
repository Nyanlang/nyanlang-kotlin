import java.io.File
import java.io.FileNotFoundException

const val regex="\"(.?(\\\\\")?)*?\""

class ParsedLoop(private val jump: Map<Int, Int>, private val next: Map<Int, Int>) {
    fun getJumpStart(currentJumpIndex: Int): Int {
        return jump[currentJumpIndex] ?: throw UnexpectedParserError("Jump start index not found, currentJumpIndex $currentJumpIndex, Map $jump")
    }

    fun getJumpEnd(currentJumpIndex: Int): Int {
        return next[currentJumpIndex] ?: throw UnexpectedParserError("Jump end index not found, currentJumpIndex $currentJumpIndex, Map $next")
    }
}

class ProgramParser {
    fun parse(programPath: String): String {
        val file = File(programPath)
        if (!file.exists()) {
            throw FileNotFoundException("File not found: ${file.absolutePath}")
        }
        if (file.extension != "nyan") {
            throw FileSuffixNotValidException(file.extension, "nyan")
        }
        val fileContents = file.readText()
        val commentRemovedContent = fileContents.replace(Regex(regex), "")
        val filteredChars = StringBuilder()
        for (char in commentRemovedContent) {
            if (char != '\n' && char != ' ') {
                filteredChars.append(char)
            }
        }
        return filteredChars.toString()
    }

    fun loopParse(program: String): ParsedLoop {
        val jump: MutableMap<Int, Int> = mutableMapOf()
        val next: MutableMap<Int, Int> = mutableMapOf()

        if (program.count {it == '~'} != program.count {it == '-'}) {
            throw SyntaxError("Loop start/end does not match.")
        }

        var nextEnd = -1

        fun findMatch(startPair: Int): Int {
            for ((innerIndex, currentChar) in program.substring(startPair+1, program.length).withIndex()) {
                val currentIndex = innerIndex + startPair+1
                when
                {
                    currentIndex <= nextEnd -> continue

                    currentChar == '~' -> nextEnd = findMatch(currentIndex)

                    currentChar == '-' -> {
                        jump[currentIndex] = startPair
                        next[startPair] = currentIndex
                        return currentIndex
                    }

                }

            }
            throw SyntaxError("Loop start/end does not match.")
        }

        for ((index, oneChar) in program.withIndex()) {
            when {
                index <= nextEnd -> continue

                oneChar == '~' -> nextEnd = findMatch(index)
            }
        }

        return ParsedLoop(jump.toMap(), next.toMap())
    }
}