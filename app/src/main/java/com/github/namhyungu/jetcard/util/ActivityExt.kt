package com.github.namhyungu.jetcard.util

import android.app.Activity

inline fun <reified T : Any> Activity.extra(name: String, defaultValue: T): Lazy<T> {
    return lazy {
        when (defaultValue) {
            is Boolean -> intent.getBooleanExtra(name, defaultValue)
            is Int -> intent.getIntExtra(name, defaultValue)
            is String -> intent.getStringExtra(name)
            else -> throw Exception("Unsupported extra (type: ${defaultValue.javaClass}, name: $name)")
        } as T? ?: defaultValue
    }
}