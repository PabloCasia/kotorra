package com.pablocasia.kotorra

actual class PlatformBranch : Branch() {
    actual override fun performLog(priority: Kotorra.Level, tag: String?, throwable: Throwable?, message: String?) {
        TODO("Not yet implemented")
    }
}
