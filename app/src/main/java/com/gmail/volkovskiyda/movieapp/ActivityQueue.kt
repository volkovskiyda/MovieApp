package com.gmail.volkovskiyda.movieapp

import androidx.fragment.app.FragmentActivity
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityQueue @Inject constructor() {

    private val queue = LinkedList<FragmentActivity.() -> Unit>()
    private val currentActivity = AtomicReference<FragmentActivity>()
    private val processing = AtomicBoolean(false)

    fun add(action: FragmentActivity.() -> Unit) {
        queue.add(action)
        process()
    }

    fun set(activity: FragmentActivity) {
        currentActivity.set(activity)
        process()
    }

    fun unset() {
        currentActivity.set(null)
    }

    private fun process() {
        if (currentActivity.get() != null && processing.compareAndSet(false, true)) {
            while (true) {
                queue.poll()?.let { action ->
                    currentActivity.get()?.let { activity -> action(activity) }
                } ?: break
            }
            processing.set(false)
        }
    }
}