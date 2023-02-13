package com.example.talechdemocoroutines

import androidx.lifecycle.ViewModel

class CooperativeFunViewModel: ViewModel() {

    private val repo = RepositoryJob()

    fun callMySilentException() {
        repo.executeSilentExceptionUsingDelay()
    }

    fun callMySilentExceptionTwo() {
        repo.executeSilentExceptionUsingWithContext()
    }

    fun callUnstoppableProcess() {
        repo.executeNoCooperativeProcess()
    }

    fun cancelMyChildrenCoroutines() {
        repo.cancelMyChildrenCoroutines()
    }
}