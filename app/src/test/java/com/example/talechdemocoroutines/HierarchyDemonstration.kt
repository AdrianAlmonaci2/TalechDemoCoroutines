package com.example.talechdemocoroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test
import java.lang.ArithmeticException
import java.lang.RuntimeException

class HierarchyDemonstration {

    @Test
    fun displayMyJobHierarchy() {
        runBlocking {
            val parentJob = Job()
            val scope = CoroutineScope(parentJob + CoroutineName("ParentJob") + Dispatchers.Default)
            val job = scope.launch(CoroutineName("Child 1 - Son of ParentJob ")) {

                launch(CoroutineName("Child 2 - Son of Child 1 ")) {
                    delay(100)
                }

                withContext(CoroutineName("Child 3 - Son of Child 1 ")) {
                    launch(CoroutineName("Child 4 - Son of Child 3 ")) {
                        delay(50)
                    }
                    printJobsHierarchy(parentJob)
                    //printJobsHierarchy(this.coroutineContext.job)
                }
                delay(100)
            }

            scope.launch(CoroutineName("Child 5 - Son of ParentJob ")) {
                delay(50)
            }

            job.join()
        }
    }

    @Test
    fun cancellationPropagation() {
        runBlocking {
            val parentJob = Job()
            val scope = CoroutineScope(
                parentJob + CoroutineName("outer scope") + Dispatchers.IO)

            var nestedCoroutineJob: Job? = null
            val job = scope.launch(CoroutineName("outer coroutine")) {
                try {
                    withContext(CoroutineName("withContext") + Dispatchers.Default) {
                        try {
                            nestedCoroutineJob = launch(CoroutineName("nested coroutine")) {
                                try {
                                    delay(1000)
                                    println("nested coroutine completed")
                                } catch (e: CancellationException) {
                                    println("nested coroutine cancelled")
                                }
                            }
                            printJobsHierarchy(parentJob)
                            delay(1000)
                            println("withContext completed")
                        } catch (e: CancellationException) {
                            println("withContext cancelled")
                        }
                    }
                    delay(1000)
                    println("outer coroutine completed")
                } catch (e: CancellationException) {
                    println("outer coroutine cancelled")
                }
            }
            launch {
                delay(500)
                scope.cancel()
            }
            job.join()
            nestedCoroutineJob?.join()
            println("test done")
            println()
        }
    }

    /**
     * Cancellation is closely related to exceptions. Coroutines internally use
     * CancellationException for cancellation, these exceptions are ignored by all handlers,
     * so they should be used only as the source of additional debug information,
     * which can be obtained by catch block.
     *
     *  CoroutineExceptionHandler implementation is not used for child coroutines.
     */

    @Test
    fun cancelMyJobHierarchy() {

        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            println("I will never be executed by a Cancellation Exception")
        }

        val exceptionChildOne = CoroutineExceptionHandler { _, t ->
            println("Child 1 has been cancelled.")
        }

        val exceptionChildTwo = CoroutineExceptionHandler { _, _ ->
            println("Child 2 has been cancelled.")
        }

        val exceptionChildThree = CoroutineExceptionHandler { _, _ ->
            println("Child 3 has been cancelled.")
        }

        runBlocking {
            val parentJob = Job()
            val scope = CoroutineScope(parentJob + CoroutineName("ParentJob") + Dispatchers.Default + exceptionHandler)

            scope.launch(CoroutineName("Child 1 - Son of ParentJob ") + exceptionChildOne) {
                try {
                    launch(CoroutineName("Child 2 - Son of Child 1 ") + exceptionChildTwo) {
                        //checkIfActiveIfNotThrowException()
                        delay(1000)
                    }

                    withContext(CoroutineName("Child 3 - Son of Child 1 ") + exceptionChildThree) {
                        printJobsHierarchy(parentJob)
                        scope.cancel()
                    }
                } catch (e: CancellationException) {
                    println("$e")
                    println()
                }
            }
            parentJob.join()
        }
    }

    private fun printJobsHierarchy(job: Job, nestLevel: Int = 0) {
        val indent = "    ".repeat(nestLevel)
        println("$indent- $job")
        for (childJob in job.children) {
            printJobsHierarchy(childJob, nestLevel + 1)
        }
        if (nestLevel == 0) {
            println()
        }
    }
}