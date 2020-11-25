package com.github.namhyungu.jetcard.model

data class Resource<T>(
    val data: T? = null,
    val loading: Boolean = false,
    val exception: Exception? = null,
) {
    val hasError: Boolean
        get() = exception != null

    val initialLoad: Boolean
        get() = data == null && loading && !hasError
}