private enum class StringKeyword(val keyword:Char)
{
    INCREASE_VALUE('?'),
    DECREASE_VALUE('!'),

    INCREASE_POINTER('냥'),
    DECREASE_POINTER('냐'),

    VALUE_AS_CHAR('.'),
    VALUE_INPUT(','),
    VALUE_AS_INT('뀨'),

    JUMP_START('~'),
    JUMP_END('-')
}

class NyanInterpreter(filename: String) {
    private val program: String = ProgramParser().parse(filename)
    private val loops = ProgramParser().loopParse(program)

    // Runtime Variables
    private var cursor: Int = 0
    private val memory: NyanMemory = NyanMemory()
    private val pointer: Pointer = Pointer()

    private val keywords: Map<Char, (NyanInterpreter) -> Unit> = mapOf(
        StringKeyword.INCREASE_VALUE.keyword to {o: NyanInterpreter -> o.pointer.increase()},
        StringKeyword.DECREASE_VALUE.keyword to {o: NyanInterpreter -> o.pointer.decrease()},

        StringKeyword.INCREASE_POINTER.keyword to {o: NyanInterpreter -> o.memory.increase(o.pointer)},
        StringKeyword.DECREASE_POINTER.keyword to {o: NyanInterpreter -> o.memory.decrease(o.pointer)},

        StringKeyword.VALUE_AS_CHAR.keyword to {o: NyanInterpreter -> print(o.memory.get(o.pointer).toChar())},
        StringKeyword.VALUE_INPUT.keyword to {o: NyanInterpreter -> o.memory.set(o.pointer, readlnOrNull()?.get(0)?.code)},
        StringKeyword.VALUE_AS_INT.keyword to {o: NyanInterpreter -> print("{"+o.memory.get(o.pointer)+"}")},

        StringKeyword.JUMP_END.keyword to {o: NyanInterpreter -> if (o.memory.get(o.pointer) != 0) {o.cursor = loops.getJumpStart(o.cursor)}},
        StringKeyword.JUMP_START.keyword to {o: NyanInterpreter -> if (o.memory.get(o.pointer) == 0) {o.cursor = loops.getJumpEnd(o.cursor)}}
    )

    fun run() {
        while (cursor < program.length) {
            val char: Char = program[cursor]

            keywords[char]?.invoke(this) ?: throw SyntaxError("Invalid character $char")
            cursor++
        }
    }
}