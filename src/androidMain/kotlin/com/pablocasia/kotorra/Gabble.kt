package com.pablocasia.kotorra

data class Gabble(
    var message: String = "",
    var tag: String? = null,
    var throwable: Throwable? = null,
    var isThreadNameVisible: Boolean = false
)
