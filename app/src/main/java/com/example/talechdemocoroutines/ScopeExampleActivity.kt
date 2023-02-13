package com.example.talechdemocoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.talechdemocoroutines.databinding.ScopeExampleActivityBinding

class ScopeExampleActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()
    private lateinit var binding: ScopeExampleActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.scope_example_activity)
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

            goToNextActivity.setOnClickListener {
                goToCooperativeFunActivity()
            }
        }
    }

    fun goToCooperativeFunActivity() {
        val intent = Intent(this, CooperativeFunctionsActivity::class.java)
        startActivity(intent)
    }
}