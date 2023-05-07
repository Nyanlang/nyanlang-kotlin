class Pointer(address: Int = 0) {
    var address: Int = address
        private set

    fun increase() {
        address++
    }

    fun decrease() {
        address--
    }

    fun set(value: Int) {
        address = value
    }

    fun get(): Int = address
}