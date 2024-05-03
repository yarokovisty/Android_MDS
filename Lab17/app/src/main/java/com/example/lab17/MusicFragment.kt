package com.example.lab17

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab17.databinding.FragmentMusicBinding
import com.google.android.material.tabs.TabLayoutMediator


class MusicFragment : Fragment() {
    private var _binding: FragmentMusicBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
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
                TypeMusicFragment(getString(R.string.pop)),
                TypeMusicFragment(getString(R.string.disco)),
                TypeMusicFragment(getString(R.string.rock)),
                TypeMusicFragment(getString(R.string.classic)),
                TypeMusicFragment(getString(R.string.soundtracks)),
                TypeMusicFragment(getString(R.string.rap))

            ),
            this
        )
        binding.vpTypeMusic.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.vpTypeMusic) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.pop)
                1 -> tab.text = getString(R.string.disco)
                2 -> tab.text = getString(R.string.rock)
                3 -> tab.text = getString(R.string.classic)
                4 -> tab.text = getString(R.string.soundtracks)
                5 -> tab.text = getString(R.string.rap)
            }
        }.attach()
    }


}