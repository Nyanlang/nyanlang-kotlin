fun main(args: Array<String>) {
    when (args[0]) {
        "run" -> {
            if (!args[1].startsWith("-")) {
                try {
                    NyanInterpreter(args[1]).run()
                } catch (e: Exception) {
                    if (e is HandledException) {
                        println("ERROR | ${e::class.simpleName} : ${e.message}")
                    } else {
                        println("Unexpected Error | ${e.stackTraceToString()}")
                    }
                }
            }
        }
    }
}