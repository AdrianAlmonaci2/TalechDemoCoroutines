package com.example.talechdemocoroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

private const val ONE_SECOND = 1_000L

class Repository {
    suspend fun sevenSecondsProcess() {
        return withContext(Dispatchers.Default) {
            var seconds = 0
            while (seconds < 7) {
                delay(ONE_SECOND)
                seconds++
                println("Seconds: $seconds")
            }
        }
    }

    suspend fun throwAnException() {
        return withContext(Dispatchers.Default) {
            var seconds = 0
            while (seconds < 7) {
                delay(ONE_SECOND)
                seconds++
                println("Seconds: $seconds")
                if (seconds == 5) {
                    throw RuntimeException()
                }
            }
        }
    }
}