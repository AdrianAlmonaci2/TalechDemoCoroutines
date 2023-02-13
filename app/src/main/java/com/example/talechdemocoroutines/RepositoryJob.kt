package com.example.talechdemocoroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

private const val ONE_SECOND = 1_000L

class RepositoryJob {

    private val parentScope = CoroutineScope(Dispatchers.Default)

    fun executeNoCooperativeProcess() {
        parentScope.launch {
            try {
                noCooperativeProcess()
            } catch (e: CancellationException) {
                printCancellation(e)
            }
        }
    }

    private suspend fun noCooperativeProcess() {
        var seconds = 0
        while (seconds < 7) {
            Thread.sleep(ONE_SECOND)
            seconds++
            println("Seconds: $seconds")
            coroutineContext.printCoroutineInfo()
        }
    }

    fun executeSilentExceptionUsingDelay() {
        parentScope.launch {
            try {
                silentExceptionUsingDelay()
            } catch (e: CancellationException) {
                printCancellation(e)
            }
        }
    }

    private suspend fun silentExceptionUsingDelay() {
        var seconds = 0
        while (seconds < 7) {
            delay(ONE_SECOND)
            seconds++
            println("Seconds: $seconds")
            coroutineContext.printCoroutineInfo()
        }
    }

    fun executeSilentExceptionUsingWithContext() {
        parentScope.launch {
            try {
                silentExceptionUsingWithContext()
            } catch (e: CancellationException) {
                printCancellation(e)
            }
        }
    }

    private suspend fun silentExceptionUsingWithContext() {
        withContext(CoroutineName("WithContext")) {
            var seconds = 0
            while (seconds < 7) {
                Thread.sleep(ONE_SECOND)
                seconds++
                println("Seconds: $seconds")
                coroutineContext.printCoroutineInfo()
            }
        }
    }

    private fun printCancellation(e: CancellationException) {
        println("My Coroutine is cancelled: $e")
        println()
    }

    fun cancelMyChildrenCoroutines() {
        parentScope.coroutineContext.cancelChildren()
    }
}