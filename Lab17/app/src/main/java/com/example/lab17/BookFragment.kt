package com.example.lab17

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab17.databinding.FragmentBookBinding
import com.google.android.material.tabs.TabLayoutMediator


class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var adapter: ViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTabLayout() {
        adapter = ViewPagerAdapter(
            listOf(
                TypeBookFragment(getString(R.string.new_book)),
                TypeBookFragment(getString(R.string.read_book)),

            ),
            this
        )
        binding.vpBook.adapter = adapter

        TabLayoutMediator(binding.tabLayout2, binding.vpBook) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.new_book)
                1 -> tab.text = getString(R.string.read_book)
            }
        }.attach()
    }

}