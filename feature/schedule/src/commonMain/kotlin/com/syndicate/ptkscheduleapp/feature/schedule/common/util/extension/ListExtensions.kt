package com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension

inline fun <T> MutableList<T>.removeIf(predicate: (T) -> Boolean): Boolean {
    val iterator = this.iterator()
    var removed = false
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.remove()
            removed = true
        }
    }
    return removed
}