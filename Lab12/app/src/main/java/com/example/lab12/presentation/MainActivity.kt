package com.example.lab12.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.lab12.R
import com.example.lab12.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FoodListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleView()
        setupClickListener()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.foodList.observe(this) {
            Log.i("MyLog", it[0].toString())
            adapter.submitList(it)
        }
    }

    private fun setupRecycleView() {
        adapter = FoodListAdapter()
        with(binding.rvFoodList) {
            adapter = this@MainActivity.adapter
            recycledViewPool.setMaxRecycledViews(
                FoodListAdapter.VIEW_TYPE_ENABLED,
                FoodListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                FoodListAdapter.VIEW_TYPE_DISABLED,
                FoodListAdapter.MAX_POOL_SIZE
            )
        }
    }

    private fun setupClickListener() {
        adapter.onFoodItemClickListener = { foodItem ->
            showSnackBar(foodItem.enabled, foodItem.name)
            viewModel.changeEnableState(foodItem)

        }
    }

    private fun showSnackBar(enabled: Boolean, nameFood: String) {
        val sb: Snackbar
        if (enabled) {
            sb = Snackbar.make(
                binding.root,
                String.format(resources.getString(R.string.add_thing), nameFood),
                Snackbar.LENGTH_SHORT
            )
            sb.setBackgroundTint(getColor(R.color.green))
            sb.show()
        } else {
            sb = Snackbar.make(
                binding.root,
                String.format(resources.getString(R.string.delete_thing), nameFood),
                Snackbar.LENGTH_SHORT
            )
            sb.setBackgroundTint(getColor(R.color.red))
            sb.show()
        }
    }

}