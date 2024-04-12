package com.example.lab16

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab16.databinding.FragmentBearBinding

class BearFragment : Fragment() {
    private var _binding: FragmentBearBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnStay.setOnClickListener {
                Log.i("MyLog", "stay")
                findNavController().navigate(R.id.action_bearFragment_to_finalBadFragment)
            }
            btnEscape.setOnClickListener {
                findNavController().navigate(R.id.action_bearFragment_to_foxFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}