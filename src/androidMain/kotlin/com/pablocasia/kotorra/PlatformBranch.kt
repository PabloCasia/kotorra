package com.pablocasia.kotorra

import android.os.Build
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

actual class PlatformBranch : Branch() {
    private val loggerTitle: String = "Kotorra_Android"

    actual override fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?) {
        require(isEnable(priority, tag)) { return }

        val stackTrace = Thread.currentThread().stackTrace
        requireNotNull(stackTrace) { return }

        val elementIndex: Int = getElementIndex(stackTrace)
        require(elementIndex > 0) { return }

        val element = stackTrace[elementIndex]
        val result = StringBuilder()

        if (Kotorra.isThreadNameVisible) addThreadName(result)
        addClassName(element, result)
        addMethodName(element, result)
        message?.let { addMessage(message, result) }
        throwable?.let { addException(it, result) }

        Log.println(priority.toValue(), getGeneratedTag(tag), result.toString())
    }

    private fun getElementIndex(stackTrace: Array<StackTraceElement>?): Int {
        requireNotNull(stackTrace) { return 0 }
        for (i in 2..stackTrace.size) {
            val className = stackTrace[i].className ?: ""
            if (className.contains("com.pablocasia.kotorra")) continue
            return i
        }
        return 0
    }

    private fun addThreadName(result: StringBuilder) {
        result.append("[Thread: ").append(Thread.currentThread().name).append("] ")
    }

    private fun addClassName(element: StackTraceElement, result: StringBuilder) {
        val fullClassName = element.className
        val classNameFormatted = fullClassName.substring(fullClassName.lastIndexOf('.') + 1)
        result.append(classNameFormatted).append(".")
    }

    private fun addMethodName(element: StackTraceElement, result: StringBuilder) {
        result.append("${element.methodName}(${element.fileName}:${element.lineNumber})")
    }

    private fun addMessage(msg: String, result: StringBuilder) {
        require(msg.isNotEmpty()) { return }
        result.append(" -> ").append(msg)
    }

    private fun addException(throwable: Throwable, result: StringBuilder) {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        printWriter.flush()
        result.append("\n Throwable: ").append(stringWriter.toString())
    }

    private fun getGeneratedTag(tag: String?): String {
        val generatedTag = if (tag == null) loggerTitle else "${loggerTitle}_$tag"
        return if (generatedTag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            generatedTag
        } else {
            generatedTag.substring(0, MAX_TAG_LENGTH)
        }
    }

    private fun Kotorra.Level.toValue() = when (this) {
        Kotorra.Level.VERBOSE -> Log.VERBOSE
        Kotorra.Level.DEBUG -> Log.DEBUG
        Kotorra.Level.INFO -> Log.INFO
        Kotorra.Level.WARNING -> Log.WARN
        Kotorra.Level.ERROR -> Log.ERROR
    }

    companion object {
        private const val MAX_TAG_LENGTH = 23
    }
}
