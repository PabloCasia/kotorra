package com.pablocasia.kotorra

actual class PlatformBranch : Branch() {
    private val loggerTitle: String = "Kotorra_Javascript"

    actual override fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?) {
        require(isEnable(priority, tag)) { return }
        val result = StringBuilder()
        addPriority(priority, result)
        addTag(tag, result)
        message?.let { addMessage(message, result) }
        throwable?.let { addException(it, result) }

        console.log("$result")
    }

    private fun addPriority(priority: Kotorra.Level, result: StringBuilder) {
        result.append("${priority.toValue()}/")
    }

    private fun addTag(tag: String?, result: StringBuilder) {
        requireNotNull(tag) { result.append(loggerTitle) }
        result.append("${loggerTitle}_$tag")
    }

    private fun addMessage(msg: String, result: StringBuilder) {
        require(msg.isNotEmpty()) { return }
        result.append(" -> ").append(msg)
    }

    private fun addException(throwable: Throwable, result: StringBuilder) {
        result.append("\n Throwable: ").append(throwable.message)
    }

    private fun Kotorra.Level.toValue() = when (this) {
        Kotorra.Level.VERBOSE -> "VERBOSE"
        Kotorra.Level.DEBUG -> "DEBUG"
        Kotorra.Level.INFO -> "INFO"
        Kotorra.Level.WARNING -> "WARNING"
        Kotorra.Level.ERROR -> "ERROR"
    }
}
