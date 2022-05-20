package com.katocoding.munrolibrarychallenge.ui.munrolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.katocoding.munrolibrarychallenge.databinding.FragmentMunrolistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MunroListFragment: Fragment() {

    private val viewModel: MunroListViewModel by viewModels()

    private var _binding: FragmentMunrolistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMunrolistBinding.inflate(inflater, container, false)
        return binding.root
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