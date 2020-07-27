package com.pablocasia.kotorra

fun Kotorra.v(block: Gabble.() -> Unit) {
    Kotorra.logGabble(block, Kotorra.Level.VERBOSE)
}

fun Kotorra.d(block: Gabble.() -> Unit) {
    Kotorra.logGabble(block, Kotorra.Level.DEBUG)
}

fun Kotorra.i(block: Gabble.() -> Unit) {
    Kotorra.logGabble(block, Kotorra.Level.INFO)
}

fun Kotorra.w(block: Gabble.() -> Unit) {
    Kotorra.logGabble(block, Kotorra.Level.WARNING)
}

fun Kotorra.e(block: Gabble.() -> Unit) {
    Kotorra.logGabble(block, Kotorra.Level.ERROR)
}

private fun Kotorra.logGabble(block: Gabble.() -> Unit, priority: Kotorra.Level) {
    with(Gabble().apply(block)) {
        val isThreadNameVisibleInitialValue = Kotorra.isThreadNameVisible
        Kotorra.isThreadNameVisible = isThreadNameVisible
        rawLog(priority, tag, throwable, message)
        Kotorra.isThreadNameVisible = isThreadNameVisibleInitialValue
    }
}
