package com.example.talechdemocoroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repositoryScope: RepositoryScope = RepositoryScope()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val handler = CoroutineExceptionHandler { _, _ ->
        println("This is my action defined in my ExceptionHandler.")
    }

    fun callMyFiveSecProcess() {
        viewModelScope.launch {
            repositoryScope.sevenSecondsProcess()
        }
    }

    fun cancelMyScope() {
        println("ViewModelScope has been canceled")
        viewModelScope.cancel()
    }

    fun cancelChildrenCoroutines() {
        println("Children coroutines have been canceled")
        viewModelScope.coroutineContext.cancelChildren()
    }

    fun throwAnException() {
        scope.launch {
            println("Throwing an exception.")
            repositoryScope.throwAnException()
        }
    }

    fun throwAnExceptionWithExceptionHandler() {
        scope.launch(handler) {
            println("Throwing an exception with an ExceptionHandler.")
            repositoryScope.throwAnException()
        }
    }

    fun throwAnExceptionWithSupervisorJob() {
        val supervisor = SupervisorJob()
        scope.launch(supervisor + handler) {
            println("Throwing an exception with a SupervisorJob.")
            repositoryScope.throwAnException()
        }
    }

    fun throwAnExceptionWithViewmodelScope() {

        viewModelScope.launch(handler) {
            println("Throwing an exception with a ViewmodelScope.")
            repositoryScope.throwAnException()
        }
    }
}