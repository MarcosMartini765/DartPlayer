package com.martini.dartplayer.common.extensions

fun <T> MutableList<T>.removeReturn(value: T): MutableList<T> {
    this.remove(value)
    return this
}

