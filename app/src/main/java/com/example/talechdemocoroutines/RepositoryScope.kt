package com.example.talechdemocoroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

private const val ONE_SECOND = 1_000L

class RepositoryScope {

    private val scope = CoroutineScope(Dispatchers.Default)

    fun sevenSecondsProcess() {
        scope.launch {
            return@launch withContext(Dispatchers.Default) {
                var seconds = 0
                while (seconds < 7) {
                    delay(ONE_SECOND)
                    seconds++
                    println("Seconds: $seconds")
                }
            }
        }
    }

    fun throwAnException() {
        scope.launch {
            return@launch withContext(Dispatchers.Default) {
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
}