package com.github.namhyungu.jetcard.startup

import android.content.Context
import androidx.startup.Initializer
import com.github.namhyungu.jetcard.BuildConfig
import timber.log.Timber

@Suppress("unused")
class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf()
}