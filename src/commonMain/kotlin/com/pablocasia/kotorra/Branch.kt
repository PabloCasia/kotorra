package com.pablocasia.kotorra

abstract class Branch {
    open fun isEnable(priority: Kotorra.Level, tag: String?) = true

    internal fun rawLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?) {
        performLog(priority, tag, throwable, message)
    }

    protected abstract fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?)
}
