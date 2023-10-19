package com.example.flowsapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.flowsapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        init()
        dataObservable()
    }

    private fun getSnack(text: String) {

        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }


    private fun init() {
        binding.liveDataBtn.setOnClickListener {
            mainViewModel.triggerLiveData()
        }
        binding.stateFlowBtn.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }

        binding.sharedFlowBtn.setOnClickListener {
            mainViewModel.triggerSharedFlow()
        }

        binding.flowBtn.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.triggerFlow().collect {
                    binding.flowText.text = it.toString()
                    getSnack(it.toString())
                }
            }
        }
    }

    private fun dataObservable() {
        mainViewModel.liveData.observe(this) {
            binding.liveDataText.text = it
            getSnack(it)
        }
        lifecycleScope.launchWhenStarted {
            mainViewModel.stateFlow.collect {
                binding.stateFlowText.text = it
                getSnack(it)
            }
        }
        lifecycleScope.launch {
            mainViewModel.sharedFlow.collect {
                binding.sharedFlowText.text = it
                getSnack(it)
            }
        }
    }
}