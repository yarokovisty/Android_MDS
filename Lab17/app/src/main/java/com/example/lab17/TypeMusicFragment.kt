package com.example.lab17

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab17.databinding.FragmentTypeMusicBinding


class TypeMusicFragment(private val typeMusic: String) : Fragment() {
    private var _binding: FragmentTypeMusicBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTypeMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTypeMusic.text = getString(R.string.type_music, typeMusic)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}