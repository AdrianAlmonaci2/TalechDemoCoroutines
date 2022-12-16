package com.example.talechdemocoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.talechdemocoroutines.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setClickListeners()
    }

    private fun setClickListeners() {
        with(binding) {

            launchCorutine.setOnClickListener {
                viewModel.callMyFiveSecProcess()
            }

            cancelScope.setOnClickListener {
                viewModel.cancelMyScope()
            }

            cancelChildCorotuine.setOnClickListener {
                viewModel.cancelChildrenCoroutines()
            }

            throwException.setOnClickListener {
                viewModel.throwAnException()
            }

            exceptionHandler.setOnClickListener {
                viewModel.throwAnExceptionWithExceptionHandler()
            }

            supervisor.setOnClickListener {
                viewModel.throwAnExceptionWithSupervisorJob()
            }

            modelScope.setOnClickListener {
                viewModel.throwAnExceptionWithViewmodelScope()
            }
        }
    }
}