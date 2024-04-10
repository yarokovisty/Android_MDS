package com.example.lab15.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lab15.R
import com.example.lab15.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[ProductListViewModel::class.java]
    }
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupRecyclerView()
        setupSwipeListener()
        binding.btnAddProduct.setOnClickListener {
            val dialogFragment = ProductDialogFragment.newInstanceAddItem()
            dialogFragment.show(childFragmentManager, ProductDialogFragment.TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    private fun setupRecyclerView() {
        adapter = ProductListAdapter()
        binding.rvListProduct.adapter = adapter

        setOnClickListener()
    }

    private fun setOnClickListener() {
        adapter.onProductItemClickListener = { productItemId ->
            val dialogFragment = ProductDialogFragment.newInstanceEditItem(productItemId.id)
            dialogFragment.show(childFragmentManager, ProductDialogFragment.TAG)
        }
    }

    private fun setupSwipeListener() {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteProductItem(item)
            }

        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvListProduct)
    }


}