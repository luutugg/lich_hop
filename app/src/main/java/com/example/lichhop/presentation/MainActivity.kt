package com.example.lichhop.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lichhop.R
import com.example.lichhop.databinding.ActivityMainBinding
import getAppColor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy { EventAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addView()
        setUpAdapter()
        oberservaleData()
    }

    private fun addView() {
        binding.llMainRoot.post {
            val heightTotal = binding.llMainRoot.height
            val widthTotal = binding.llMainRoot.width
            for (i in 8..17) {
                val tvTime8Hours = TextView(this)
                tvTime8Hours.apply {
                    text = "$i:00:00"
                    setTextColor(getAppColor(R.color.black))
                    gravity = Gravity.CENTER
                }

                binding.llMainRoot.addView(tvTime8Hours)

                val params = tvTime8Hours.layoutParams
                params.height = heightTotal / 10
                params.width = widthTotal
                tvTime8Hours.layoutParams = params
            }
        }
    }

    private fun setUpAdapter(){
        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false )
    }

    private fun oberservaleData() {
        lifecycleScope.launchWhenCreated {
            viewModel.eventState.collect {
                binding.llMainRoot.post {
                    val heightTotal = binding.llMainRoot.height
                    val widthTotal = binding.llMainRoot.width
                    val list = it?.cacularEventList?.map {
                        CacularEventDisplay(
                            data = it,
                            widthTotal = widthTotal,
                            heightTotal = heightTotal
                        )
                    }
                    adapter.submitList(list)
                }
            }
        }
    }
}

