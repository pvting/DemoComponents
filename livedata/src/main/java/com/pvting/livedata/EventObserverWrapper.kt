package com.pvting.livedata

import android.util.Log
import androidx.lifecycle.Observer

class EventObserverWrapper<T>(
    liveData: EventLiveData<T>,
    sticky: Boolean,
    private val observerDelegate: Observer<in T>
) : Observer<T> {

    private var preventNextEvent = false

    companion object {
        private const val START_VERSION = -1
    }

    init {
        if (!sticky) {
            val clazz = Class.forName("androidx.lifecycle.LiveData")
            val field = clazz.getDeclaredField("mVersion")
            field.isAccessible = true
            val version = field.get(liveData) as Int
//            val version = ReflectHelper.of(liveData).getField("mVersion") as? Int ?: START_VERSION
            Log.d("versionId", "$version")
            preventNextEvent = version > START_VERSION
        }
    }

    override fun onChanged(t: T) {
        if (preventNextEvent) {
            preventNextEvent = false
            return
        }
        observerDelegate.onChanged(t)
    }
}