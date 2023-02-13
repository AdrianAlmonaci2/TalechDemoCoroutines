package com.example.talechdemocoroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

fun CoroutineContext.printCoroutineInfo() {
    println()
    println("Is my job still active? $isActive")
}