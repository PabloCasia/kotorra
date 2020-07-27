package com.pablocasia.kotorra

import kotlin.jvm.JvmOverloads
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Kotorra {
    private val tree = mutableListOf<Branch>()
    var isThreadNameVisible = false

    @JvmOverloads
    fun initialize(branches: List<Branch> = listOf(PlatformBranch())) {
        branches.forEach { tree.add(it) }
    }

    fun removeBranch(branch: Branch) {
        tree.remove(branch)
    }

    fun clearBranches() {
        tree.clear()
    }

    @PublishedApi
    internal fun rawLog(priority: Level, tag: String?, throwable: Throwable?, message: String?) {
        if (isEnable(priority, tag)) tree.forEach { it.rawLog(priority, tag, throwable, message) }
    }

    @JvmOverloads
    fun v(message: String = "", tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.VERBOSE, tag, throwable, message)
    }

    @JvmOverloads
    fun v(message: () -> String, tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.VERBOSE, tag, throwable, message())
    }

    @JvmOverloads
    fun d(message: String = "", tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.DEBUG, tag, throwable, message)
    }

    @JvmOverloads
    fun d(message: () -> String, tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.DEBUG, tag, throwable, message())
    }

    @JvmOverloads
    fun i(message: String = "", tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.INFO, tag, throwable, message)
    }

    @JvmOverloads
    fun i(message: () -> String, tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.INFO, tag, throwable, message())
    }

    @JvmOverloads
    fun w(message: String = "", tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.WARNING, tag, throwable, message)
    }

    @JvmOverloads
    fun w(message: () -> String, tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.WARNING, tag, throwable, message())
    }

    @JvmOverloads
    fun e(message: String = "", tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.ERROR, tag, throwable, message)
    }

    @JvmOverloads
    fun e(message: () -> String, tag: String? = null, throwable: Throwable? = null) {
        rawLog(Level.ERROR, tag, throwable, message())
    }

    private fun isEnable(priority: Level, tag: String?) = tree.any { it.isEnable(priority, tag) }

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }
}
