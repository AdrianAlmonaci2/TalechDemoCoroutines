package com.example.talechdemocoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.talechdemocoroutines.databinding.ActivityCooperativeFunctionsBinding
import kotlinx.coroutines.CancellationException

class CooperativeFunctionsActivity : AppCompatActivity() {

    private val viewModel = CooperativeFunViewModel()
    lateinit var binding: ActivityCooperativeFunctionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cooperative_functions)
        setBinding()
    }

    private fun setBinding() {
        with(binding) {
            model = viewModel
            lifecycleOwner = this@CooperativeFunctionsActivity

            funZero.setOnClickListener {
                callMySilentException()
            }

            funOne.setOnClickListener {
                callMySilentExceptionTwo()
            }

            funTwo.setOnClickListener {
                callMySilentExceptionThree()
            }
        }
    }

    private fun callMySilentException() {
        viewModel.callUnstoppableProcess()
    }

    private fun callMySilentExceptionTwo() {
        try {
            viewModel.callMySilentException()
        } catch (e: CancellationException) {
            println("Mi cancellation Two")
        }
    }

    private fun callMySilentExceptionThree() {
        try {
            viewModel.callMySilentExceptionTwo()
        } catch (e: CancellationException) {
            println("Mi cancellation Three")
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.cancelMyChildrenCoroutines()
    }
}