package com.katocoding.munrolibrarychallenge.ui.munrofilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.katocoding.munrolibrarychallenge.databinding.FragmentMunrofilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MunroFilterFragment: Fragment() {

    private val viewModel: MunroFilterViewModel by viewModels()

    private var _binding: FragmentMunrofilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMunrofilterBinding.inflate(inflater, container, false)
        initViews()
        initObservers()
        return binding.root
    }

    private fun initViews() {

    }

    private fun initObservers() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}