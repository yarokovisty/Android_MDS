package com.example.lab17

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab17.databinding.FragmentTypeBookBinding


class TypeBookFragment(val typeBook: String) : Fragment() {
    private var _binding: FragmentTypeBookBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTypeBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTypeBook.text = getString(R.string.type_book, typeBook)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}