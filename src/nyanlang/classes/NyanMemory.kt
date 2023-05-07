class NyanMemory(private val memory: MutableMap<Int, Int> = mutableMapOf()) {
    fun increase(pointer: Pointer) {
        memory[pointer.get()] = memory.getOrDefault(pointer.get(), 0) + 1
    }

    fun decrease(pointer: Pointer) {
        memory[pointer.get()] = memory.getOrDefault(pointer.get(), 0) - 1
    }

    fun set(pointer: Pointer, value: Int?) {
        memory[pointer.get()] = value ?: 0
    }

    fun get(pointer: Pointer): Int = memory.getOrDefault(pointer.get(), 0)
}