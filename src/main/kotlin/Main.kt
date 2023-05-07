fun main(args: Array<String>) {
    when (args[0]) {
        "run" -> {
            if (!args[1].startsWith("-")) {
                try {
                    NyanInterpreter(args[1]).run()
                } catch (e: HandledException) {
                    println("ERROR | ${e::class.simpleName} : ${e.message}")
                } catch (e: Exception) {
                    println("Unexpected Error | ${e.stackTraceToString()}")
                }
            }
        }
    }
}