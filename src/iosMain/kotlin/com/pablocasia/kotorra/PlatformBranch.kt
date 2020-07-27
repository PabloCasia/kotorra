package com.pablocasia.kotorra

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSThread

actual class PlatformBranch actual constructor() : Branch() {
    private val loggerTitle: String = "Kotorra_iOS"

    private val dateFormatter = NSDateFormatter().apply {
        dateFormat = "yyyy-MM-dd HH:mm:ss.SSS"
    }

    actual override fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?) {
        require(isEnable(priority, tag)) { return }
        val stackTrace = NSThread.callStackSymbols
        val result = StringBuilder()
        addCurrentTime(result)
        addPriority(priority, result)
        addTag(tag, result)
        addClassAndMethodName(stackTrace[0] as String, result)
        message?.let { addMessage(message, result) }
        throwable?.let { addException(it, result) }

        println("$result")
    }

    private fun addCurrentTime(result: StringBuilder) {
        result.append(dateFormatter.stringFromDate(NSDate()))
    }

    private fun addPriority(priority: Kotorra.Level, result: StringBuilder) {
        result.append(" ${priority.toValue()}/")
    }

    private fun addTag(tag: String?, result: StringBuilder) {
        result.append(if (tag == null) loggerTitle else "${loggerTitle}_$tag").append(": ")
    }

    private fun addClassAndMethodName(element: String, result: StringBuilder) {
        // TODO
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
        Kotorra.Level.WARNING -> "WARN"
        Kotorra.Level.ERROR -> "ERROR"
    }
}
