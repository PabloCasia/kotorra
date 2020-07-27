package com.pablocasia.kotorra

expect class PlatformBranch() : Branch {
    override fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?)
}